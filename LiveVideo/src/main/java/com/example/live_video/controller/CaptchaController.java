package com.example.live_video.controller;

import com.example.live_video.wrapper.PassToken;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.servlet.CaptchaServlet;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@PassToken
public class CaptchaController {

    public static Captcha captcha;

    @RequestMapping("/api/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        captcha = new SpecCaptcha(130, 48, 4);
        CaptchaUtil.out(captcha, request, response);
    }

    public static boolean ver(String code) {
        return captcha.text().equalsIgnoreCase(code);
    }
}
