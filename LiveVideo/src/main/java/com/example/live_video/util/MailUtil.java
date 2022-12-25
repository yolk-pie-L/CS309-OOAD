package com.example.live_video.util;

import com.example.live_video.exception.MyException;

import java.util.Date;

public class MailUtil {

    private static Date fromDate;
    private static String verificationCode;

    public static void create(Date date, String code) {
        fromDate = date;
        verificationCode = code;
    }

    public static String getCode() {
        return verificationCode;
    }
    public static boolean ver(String code) throws MyException {
        if (verificationCode == null)
            throw new MyException("请发送邮箱验证");
        if (isOvertime(new Date())) {
            verificationCode = null;
            throw new MyException("验证码已失效");
        }
        if (verificationCode.equalsIgnoreCase(code)) {
            verificationCode = null;
            return true;
        }
        return false;
    }

    public static boolean isOvertime(Date toDate) {
        return (int) (toDate.getTime() - fromDate.getTime()) / (60 * 1000) > 1;
    }
}
