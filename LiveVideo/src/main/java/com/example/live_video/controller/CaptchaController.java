package com.example.live_video.controller;

import com.example.live_video.exception.MyException;
import com.example.live_video.service.MailService;
import com.example.live_video.util.RandomUtils;
import com.example.live_video.wrapper.PassToken;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.servlet.CaptchaServlet;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@PassToken
public class CaptchaController {

    public static Captcha captcha;

    public static String verificationCode;
    @Autowired
    private MailService mailService;
    @RequestMapping("/api/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        captcha = new SpecCaptcha(130, 48, 4);
        CaptchaUtil.out(captcha, request, response);
    }

    public static boolean ver(String code) {
        return captcha.text().equalsIgnoreCase(code);
    }

    @PostMapping("/api/mail")
    public void mail(@RequestParam String mail) throws MyException {
        mailService.sendTextMailMessage(new String[]{mail}, "Verify Your Mail", verificationCode = RandomUtils.getVerificationCode());
    }
}
