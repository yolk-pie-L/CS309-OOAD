package com.example.live_video.dto;

import lombok.Data;

@Data
public class FileForm {
    private String key;

    private String fileName;

    private Long shardIndex;

    private Long shardSize;

    private Long shardTotal;

    private Long size;

    private String suffix;

    private Long courseId;

    private String sectionName;

    private Long sectionScore;
}
