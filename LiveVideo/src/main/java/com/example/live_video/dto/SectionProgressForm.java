package com.example.live_video.dto;

import lombok.Data;

@Data
public class SectionProgressForm {
    long sectionId;
    long studentId;
    long duration;
    long currentWatch;
}
