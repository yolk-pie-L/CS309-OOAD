package com.example.live_video.controller;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.live_video.config.AlipayConfig;
import com.example.live_video.dto.PayForm;
import com.example.live_video.entity.AlipayBean;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserOrder;
import com.example.live_video.service.PaymentService;
import com.example.live_video.service.UserOrderService;
import com.example.live_video.service.UserService;
import com.example.live_video.vo.StringVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@PassToken
@ResponseResult
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserOrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    AlipayConfig alipayConfig;
    @PostMapping("/api/alipay")
    public StringVo toAlipay(@RequestBody AlipayBean alipayBean, @RequestParam String userName) throws AlipayApiException {
        System.out.println(alipayBean);
        UserOrder order = new UserOrder();
        order.setUserName(userName);
        order.setOutTradeNo(alipayBean.getOut_trade_no());
        order.setStatus(UserOrder.StuOrderStatus.CREATED);
        orderService.save(order);
        return new StringVo(paymentService.toAlipay(alipayBean));
    }

    @GetMapping("/api/alipay/notify")
    public StringVo notify(HttpServletRequest request) {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }
            String tradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");
            System.out.println("交易名称: " + params.get("subject"));
            System.out.println("交易状态: " + params.get("trade_status"));
            System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
            System.out.println("商户订单号: " + params.get("out_trade_no"));
            System.out.println("交易金额: " + params.get("total_amount"));
            System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
            System.out.println("买家付款时间: " + params.get("gmt_payment"));
            System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
            System.out.println("Sign: " + params.get("sign"));
            // 更新订单为已支付(4)
        }
        return new StringVo("通知成功");
    }

    @PostMapping("/api/alipay/success")
    public StringVo success(@RequestBody PayForm payForm) {
        UserOrder order = orderService.getById(payForm.getNo());
        if (!order.getStatus().equals(UserOrder.StuOrderStatus.APPROVED)) {
            order.setStatus(UserOrder.StuOrderStatus.APPROVED);
            orderService.updateById(order);
            User user = userService.getUser(order.getUserName());
            user.setAccount((long) (user.getAccount() + payForm.getTotalAmount()));
            // 更新订单为已支付(4)
            userService.updateUser(user);
            System.out.println(user);
            return new StringVo("交易成功");
        } else {
            return new StringVo("已更新");
        }
    }
}
