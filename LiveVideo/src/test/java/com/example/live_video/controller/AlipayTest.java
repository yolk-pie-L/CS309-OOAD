package com.example.live_video.controller;

import com.alipay.api.AlipayClient;
import com.example.live_video.config.AlipayConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
public class AlipayTest {

    @Autowired
    AlipayConfig alipayConfig;

    @Test
    public void main() {
        System.out.println(alipayConfig.toString());
    }
}
