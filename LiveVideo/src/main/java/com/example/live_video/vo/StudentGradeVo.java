package com.example.live_video.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.Data;

import java.util.List;

@Data
public class StudentGradeVo {

    @Excel(isWrap = false, name = "学生姓名", width = 30, orderNum = "1", needMerge = true)
    String studentName;

    @ExcelCollection(name = "视频成绩", orderNum = "3")
    List<StudentSectionGradeVo> studentSectionGradeList;

    @ExcelCollection(name = "作业成绩", orderNum = "2")
    List<StudentAssignGradeVo> studentAssignGradeList;
}
