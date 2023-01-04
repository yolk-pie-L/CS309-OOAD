package com.example.live_video.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class StudentSectionProgressVo {

    String sectionName;

    double studentProgress;

    int studentGrade;
}
