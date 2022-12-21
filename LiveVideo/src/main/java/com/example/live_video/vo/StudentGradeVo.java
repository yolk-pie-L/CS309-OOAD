package com.example.live_video.vo;

import lombok.Data;

import java.util.List;

@Data
public class StudentGradeVo {
    String studentName;
    List<StudentSectionGradeVo> studentSectionGradeList;
    List<StudentAssignGradeVo> studentAssignGradeList;
}
