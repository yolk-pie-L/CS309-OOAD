package com.example.live_video.util;

import java.util.Map;
import java.util.Random;

public class RandomUtils {
    private static Random random = new Random();

    private static String repo = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    public static String VID(int length) {
        StringBuffer sb = new StringBuffer();
        while (length-- > 0) {
            sb.append(repo.charAt(nextInt(0, repo.length()-1)));
        }
        return sb.toString();
    }

    public static int nextInt(int a, int b) {
        return Math.abs(random.nextInt()) % (b-a) + a;
    }

    public static void main(String[] args) {
        System.out.println(VID(10));
    }

    public static String getVerificationCode(){
        return VID(6);
    }
}
