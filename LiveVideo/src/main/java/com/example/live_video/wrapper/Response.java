package com.example.live_video.wrapper;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response implements Serializable {
    private int code;
    private String message;
    private long timestamp;
    private Object result;

    public Response(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.timestamp = System.currentTimeMillis();
        this.result = data;
    }

    public Response(ResultCode resultCode) {
        this(resultCode, null);
    }

    public static Response success(Object data) {
        return new Response(ResultCode.SUCCESS, data);
    }

    public static Response fail(Object data) { return new Response(ResultCode.FAIL, data); }

    public static Response failHttpError(Object data) {
        return new Response(ResultCode.HTTP_ERROR, data);
    }
}
