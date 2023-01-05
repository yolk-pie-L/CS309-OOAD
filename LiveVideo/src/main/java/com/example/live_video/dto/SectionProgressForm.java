package com.example.live_video.dto;

import lombok.Data;

@Data
public class SectionProgressForm {
    long sectionId;
    String studentName;
    double totalWatch;
    double currentWatch;
    double videoTime;
}
