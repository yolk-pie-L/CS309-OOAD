package com.example.live_video.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
public class AssignForm {

    Long courseId;

    String assignmentName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date deadline;

    String description;

    int totalGrade;

    List<String> additionalResources;
}
