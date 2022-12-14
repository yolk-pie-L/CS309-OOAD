package com.example.live_video.service.impl;

import com.example.live_video.exception.MyException;
import com.example.live_video.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;

@Service
public class MailServiceImpl implements MailService {
    /**
     * 注入邮件工具类
     */
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    public void sendTextMailMessage(String[] to, String subject, String text) throws MyException {
        try {
            // true 代表支持复杂的类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            mimeMessageHelper.setFrom(sendMailer); //邮件发信人
            mimeMessageHelper.setTo(to); //邮件收信人  1或多个
            mimeMessageHelper.setSubject(subject); //邮件主题
            mimeMessageHelper.setText(text); //邮件内容
            mimeMessageHelper.setSentDate(new Date()); //邮件发送时间
            javaMailSender.send(mimeMessageHelper.getMimeMessage()); //发送邮件
        } catch (MessagingException e) {
            throw new MyException(e.getMessage());
        }
    }
}
