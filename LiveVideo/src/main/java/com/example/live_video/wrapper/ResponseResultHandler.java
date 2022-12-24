package com.example.live_video.wrapper;

import com.example.live_video.exception.MyException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

@Slf4j
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {
    public static final String RESPONSE_RESULT_ANN = "RESPONSE-RESULT-ANN";

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        //判断请求是否有RESPONSE_RESULT_ANN标记
        ResponseResult responseResult = (ResponseResult) request.getAttribute(RESPONSE_RESULT_ANN);
        log.info("===============");
        //返回true 才会执行下面的方法，否则跳过不执行
        return responseResult == null;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //根据controller返回的响应类型 进行返回结果包装。controller可返回各种类型的exception或各类型数据 然后此处控制返回不同的响应：code、msg都不同
        System.out.println("Before Body Write "+o);
        if (o instanceof MyException) {
            return Response.failHttpError(((MyException) o).getMessage());
            //如果自身产生异常。LinkedHashMap为http异常
        } else if (o instanceof Exception) {
            return Response.fail(((Exception) o).getMessage());
            //如果没有报错
        } else {
            return Response.success(o);
        }
    }


}
