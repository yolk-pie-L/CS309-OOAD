package com.example.live_video.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName(value = "stu_section")
@AllArgsConstructor
@NoArgsConstructor
public class StuSection {
    @TableField(value = "user_id")
    long userId;

    @TableField(value = "section_id")
    long sectionId;

    int grade;

    double duration;

    @TableField(value = "current_watch")
    double currentWatch;
}
