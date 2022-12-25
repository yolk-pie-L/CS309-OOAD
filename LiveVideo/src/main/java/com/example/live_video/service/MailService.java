package com.example.live_video.service;

import com.example.live_video.exception.MyException;

public interface MailService {

    public void sendTextMailMessage(String[] to, String subject, String text) throws MyException;
}
