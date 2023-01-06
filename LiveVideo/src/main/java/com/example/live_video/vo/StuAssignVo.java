package com.example.live_video.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuAssignVo {
    Long assignId;
    String studentName;
    String assignmentName;
    int totalGrade;
    List<String> answers;
}
