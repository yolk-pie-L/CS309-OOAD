package com.example.live_video.controller;

import com.example.live_video.config.AlipayConfig;
import com.example.live_video.entity.AlipayBean;
import com.example.live_video.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    AlipayConfig alipayConfig;
    @PostMapping("alipay")
    public String toAlipay(@RequestBody AlipayBean alipayBean) throws IOException {
        return paymentService.toAlipay(alipayBean);
    }
}
