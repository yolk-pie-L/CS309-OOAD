package com.example.live_video.dto;

import lombok.Data;

@Data
public class ExceptionMessage {

    private String state;

    private String userType;
    private String errorMsg;

    public ExceptionMessage(String state, String userType, String errorMsg) {
        this.state = state;
        this.errorMsg = errorMsg;
    }

    public ExceptionMessage(String state, String errorMsg) {
        this(state, null, errorMsg);
    }

    public ExceptionMessage(String state) {
        this(state, null, null);
    }
}
