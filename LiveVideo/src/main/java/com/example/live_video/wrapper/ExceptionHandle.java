package com.example.live_video.wrapper;

import com.example.live_video.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Object handle(Exception e) {
        System.out.println(e.getMessage());
        if (!(e instanceof MyException)) {
            LOGGER.error("Error{}", e);
        }
        return e;
    }
}
