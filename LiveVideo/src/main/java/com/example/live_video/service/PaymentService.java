package com.example.live_video.service;

import com.example.live_video.entity.AlipayBean;

public interface PaymentService {
    String toAlipay(AlipayBean alipayBean);
}
