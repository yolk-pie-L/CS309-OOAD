package com.example.live_video.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class StudentSectionGradeVo {

    @Excel(isWrap = false, name = "章节名")
    String sectionName;

    @Excel(isWrap = false, name = "满分")
    int fullGrade;

    @Excel(isWrap = false, name = "得分")
    int studentGrade;
}
