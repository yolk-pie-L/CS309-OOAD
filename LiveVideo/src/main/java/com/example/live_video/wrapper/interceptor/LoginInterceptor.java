package com.example.live_video.wrapper.interceptor;

import com.example.live_video.exception.MyException;
import com.example.live_video.util.TokenUtils;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws  Exception {
        assert request.getHeader("token") != null;
        String token = request.getHeader("token");
        if (!(handler instanceof HandlerMethod))
            return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Class<?> clazz = handlerMethod.getBeanType();
        System.out.println(handlerMethod.getBeanType());
        System.out.println(clazz.getName());

        if (method.isAnnotationPresent(PassToken.class) || clazz.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = null;
            if (method.isAnnotationPresent(PassToken.class))
                passToken = method.getAnnotation(PassToken.class);
            if (clazz.isAnnotationPresent(PassToken.class))
                passToken = clazz.getAnnotation(PassToken.class);
            assert passToken != null;
            if (passToken.required()) {
                return true;
            }
        }

        if (method.isAnnotationPresent(UserLoginToken.class) || clazz.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken loginToken = null;
            if (method.isAnnotationPresent(UserLoginToken.class))
                loginToken = method.getAnnotation(UserLoginToken.class);
            if (clazz.isAnnotationPresent(UserLoginToken.class))
                loginToken = clazz.getAnnotation(UserLoginToken.class);
            assert loginToken != null;
            if (loginToken.required()) {

                if (token == null) {
                    throw new MyException("无token，请重新登录");
                }

                if (TokenUtils.verify(token)) {
                    request.setAttribute("user", TokenUtils.getUserName(token));
                    return true;
                } else {
                    throw new MyException("token过期或不正确，请重新登录");
                }

            }
        }
        throw new MyException("无注解无法登录");
    }
}
