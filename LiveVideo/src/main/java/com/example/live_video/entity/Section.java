package com.example.live_video.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@TableName(value = "section")
public class Section {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "section_name")
    private String sectionName;

    @TableField(exist = false)
    private String courseName;

    @TableField(exist = false)
    private String teacherName;

    @TableField(value = "course_id")
    private Long courseId;

    @TableField(value = "video_url")
    private String videoUrl;

    @TableField(value = "create_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp createTime;

    @TableField(value = "update_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp updateTime;

    @TableLogic(value = "0", delval = "id")
    @TableField(value = "is_delete", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Integer deleteFlag;

    public Section(String sectionName, String courseName, String teacherName, String videoUrl) {
        this.sectionName = sectionName;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.videoUrl = videoUrl;
    }

    public Section(String sectionName, Long courseId, String videoUrl) {
        this.sectionName = sectionName;
        this.courseId = courseId;
        this.videoUrl = videoUrl;
    }
}
