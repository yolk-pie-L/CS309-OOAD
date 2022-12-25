package com.example.live_video.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.live_video.entity.User;
import com.example.live_video.exception.MyException;

import java.sql.Date;

public class TokenUtils {

    //token到期时间10小时
    private static final long EXPIRE_TIME= 10*60*60*1000;
    //密钥盐
    private static final String TOKEN_SECRET="ljdyaishijin**3nkjnj??";

    /**
     * 生成token
     * @param userName
     * @return
     */
    public static String sign(String userName) throws Exception{

        String token;
        Date expireAt=new Date(System.currentTimeMillis()+EXPIRE_TIME);
        token = JWT.create()
                //发行人
                .withIssuer("auth0")
                //存放数据
                .withClaim("username", userName)
                //过期时间
                .withExpiresAt(expireAt)
                .sign(Algorithm.HMAC256(TOKEN_SECRET));
        return token;
    }


    /**
     * token验证
     * @param token
     * @return
     */
    public static Boolean verify(String token) throws Exception{

        try {
            //创建token验证器
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT decodedJWT=jwtVerifier.verify(token);
            System.out.println("认证通过：");
            System.out.println("username: " + decodedJWT.getClaim("username").asString());
            System.out.println("过期时间：      " + decodedJWT.getExpiresAt());
        } catch (IllegalArgumentException | JWTVerificationException e) {
            return false;
        }
        return true;
    }

    public static String getUserName(String token) throws Exception{
        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
        DecodedJWT decodedJWT=jwtVerifier.verify(token);
        return decodedJWT.getClaim("username").asString();
    }

}
