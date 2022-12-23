package com.example.live_video.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class StudentAssignGradeVo {

    @Excel(isWrap = false, name = "作业名", width = 10, orderNum = "1")
    String assignName;

    @Excel(isWrap = false, name = "满分", width = 10, orderNum = "3")
    int totalGrade;

    @Excel(isWrap = false, name = "得分", width = 10, orderNum = "2")
    int studentGrade;
}
