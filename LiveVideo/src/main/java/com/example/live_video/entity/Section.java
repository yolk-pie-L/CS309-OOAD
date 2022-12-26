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

    @TableField(value = "course_id", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Long courseId;

    @TableField(value = "video_url", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String videoUrl;

    @TableField(value = "grade", updateStrategy = FieldStrategy.NOT_EMPTY)
    private int grade;

    @TableField(value = "create_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp createTime;

    @TableField(value = "update_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp updateTime;

    /**
     * For upper layer
     * @param sectionName
     * @param courseName
     * @param teacherName
     * @param videoUrl
     */
    public Section(String sectionName, String courseName, String teacherName, String videoUrl) {
        this.sectionName = sectionName;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.videoUrl = videoUrl;
    }

    /**
     * For database layer
     * @param sectionName
     * @param courseId
     * @param videoUrl
     */
    public Section(String sectionName, Long courseId, String videoUrl, int grade) {
        this.sectionName = sectionName;
        this.courseId = courseId;
        this.videoUrl = videoUrl;
        this.grade = grade;
    }
}
