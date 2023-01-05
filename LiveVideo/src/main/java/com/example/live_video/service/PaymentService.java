package com.example.live_video.service;

import com.alipay.api.AlipayApiException;
import com.example.live_video.entity.AlipayBean;

public interface PaymentService {
    String toAlipay(AlipayBean alipayBean) throws AlipayApiException;
}
