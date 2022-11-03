package com.example.live_video.dto;

import lombok.Data;

@Data
public class ExceptionMessage {

    private String state;

    private String errorMsg;

    public ExceptionMessage(String state, String errorMsg) {
        this.state = state;
        this.errorMsg = errorMsg;
    }

    public ExceptionMessage(String state) {
        this(state, null);
    }
}
