package com.example.live_video.dto;

import lombok.Data;

@Data
public class JoinForm {

    private String studentName;

    private Long courseId;

    @Override
    public String toString() {
        return "studentName: " + studentName + " courseId: " + courseId;
    }
}
