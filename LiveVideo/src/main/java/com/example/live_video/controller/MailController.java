package com.example.live_video.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.live_video.exception.MyException;
import com.example.live_video.service.MailService;
import com.example.live_video.service.UserService;
import com.example.live_video.util.MailUtil;
import com.example.live_video.util.RandomUtils;
import com.example.live_video.util.TokenUtils;
import com.example.live_video.wrapper.PassToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@PassToken
@RequestMapping("/api/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public Boolean mail(@RequestParam String mail) throws MyException {
        MailUtil.create(new Date(), RandomUtils.getVerificationCode());
        mailService.sendTextMailMessage(new String[]{mail}, "Please Verify Your Mail", "尊敬的用户,您好:\n"
                + "\n感谢您注册LiveVideo网站！本次请求的邮件验证码为:" + MailUtil.getCode() + ",本验证码5分钟内有效，请及时输入。（请勿泄露此验证码）\n"
                + "\n如非本人操作，请忽略该邮件。\n(这是一封自动发送的邮件，请不要直接回复）");
        return true;
    }

    @PostMapping("/modify")
    public Boolean modifyMail(@RequestHeader String token) throws Exception {
        String mail = userService.getUser(TokenUtils.getUserName(token)).getMail();
        MailUtil.create(new Date(), RandomUtils.getVerificationCode());
        mailService.sendTextMailMessage(new String[]{mail}, "Please Verify Your Mail", "尊敬的用户,您好:\n"
                + "\n您正在修改密码！本次请求的邮件验证码为:" + MailUtil.getCode() + ",本验证码5分钟内有效，请及时输入。（请勿泄露此验证码）\n"
                + "\n如非本人操作，请忽略该邮件。\n(这是一封自动发送的邮件，请不要直接回复）");
        return true;
    }
}
