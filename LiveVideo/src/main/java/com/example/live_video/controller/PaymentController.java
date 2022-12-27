package com.example.live_video.controller;

import com.alipay.api.AlipayApiException;
import com.example.live_video.config.AlipayConfig;
import com.example.live_video.entity.AlipayBean;
import com.example.live_video.service.PaymentService;
import com.example.live_video.vo.StringVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@PassToken
@ResponseResult
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    AlipayConfig alipayConfig;
    @PostMapping("/api/alipay")
    public StringVo toAlipay(@RequestBody AlipayBean alipayBean) throws AlipayApiException {
        alipayBean.setOut_trade_no(UUID.randomUUID().toString().replaceAll("-",""));
        System.out.println(alipayBean);
        return new StringVo(paymentService.toAlipay(alipayBean));
    }

    @GetMapping("/api/alipay/success")
    public StringVo success(@RequestParam(value = "out_trade_no") String tradeNo,
                            @RequestParam(value = "total_amount") String totalAmount) {
        System.out.println(tradeNo);
        System.out.println(totalAmount);
        return new StringVo("交易成功");
    }
}
