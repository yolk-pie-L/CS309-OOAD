package com.example.live_video.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@TableName(value = "notice")
public class Notice {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "notice_name")
    private String noticeName;

    @TableField(value = "course_id")
    private Long courseId;

    @TableField(exist = false)
    private String teacherName;

    @TableField(exist = false)
    private String courseName;

    @TableField(value = "create_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp createTime;

    @TableField(value = "context", insertStrategy = FieldStrategy.NOT_EMPTY)
    private String context;


    public Notice(String teacherName, String courseName, String noticeName, String context) {
        this.teacherName = teacherName;
        this.courseName = courseName;
        this.noticeName = noticeName;
        this.context = context;
    }

    public Notice(String noticeName, Long courseId, String context) {
        this.noticeName = noticeName;
        this.courseId = courseId;
        this.context = context;
    }
}
