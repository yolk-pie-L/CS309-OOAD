package com.example.live_video.dto;

import lombok.Data;

@Data
public class NoticeForm {
    Long courseId;
    String title;
    String context;
    boolean sendMail;
}
