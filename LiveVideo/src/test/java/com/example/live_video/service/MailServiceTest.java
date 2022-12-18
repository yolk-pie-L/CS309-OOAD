package com.example.live_video.service;

import com.example.live_video.exception.MyException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailServiceTest {

    @Autowired
    MailService mailService;

    @Test
    void sendTextMailMessage() throws MyException {
        mailService.sendTextMailMessage(new String[]{"12013029@mail.sustech.edu.cn","12010117@mail.sustech.edu.cn","12013027@mail.sustech.edu.cn","12010538@mail.sustech.edu.cn"}, "SUSTech Live Video", "hello world!");
    }
}