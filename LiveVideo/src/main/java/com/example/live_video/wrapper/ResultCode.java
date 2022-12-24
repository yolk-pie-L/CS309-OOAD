package com.example.live_video.wrapper;

public enum ResultCode {

    SUCCESS(200, "OK"),
    FAIL(500, "Fail"),
    HTTP_ERROR(250, "HTTP Error"),

    TOKEN_EXPIRED(251, "Token Expired");
    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
