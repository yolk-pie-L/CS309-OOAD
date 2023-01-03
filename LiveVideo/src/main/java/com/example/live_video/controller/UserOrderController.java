package com.example.live_video.controller;

import com.example.live_video.dto.UserOrderForm;
import com.example.live_video.entity.UserOrder;
import com.example.live_video.service.UserOrderService;
import com.example.live_video.wrapper.PassToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@PassToken
@RequestMapping("/api/userorder")
public class UserOrderController {

    @Autowired
    UserOrderService userOrderService;

    @PostMapping("/del/{outTradeNo}")
    boolean deleteUserOrder(@PathVariable String outTradeNo){
        userOrderService.removeById(outTradeNo);
        return true;
    }

    @PostMapping("/update")
    boolean updateUserOrder(@RequestBody UserOrderForm userOrderForm){
        UserOrder userOrder = new UserOrder(userOrderForm.getOutTradeNo(), null,
                UserOrder.StuOrderStatus.valueOf(userOrderForm.getStatus()));
        userOrderService.updateById(userOrder);
        return true;
    }

    @PostMapping("/create")
    boolean createUserOrder(@RequestBody UserOrder userOrder){
        userOrderService.save(userOrder);
        return true;
    }
}
