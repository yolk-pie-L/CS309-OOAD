package com.example.live_video.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@TableName(value = "assignment")
public class Assignment {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "assignment_name")
    private String assignmentName;

    @TableField(value = "course_id")
    private Long courseId;

    @TableField(exist = false)
    private String courseName;

    @TableField(exist = false)
    private String teacherName;

    @TableField(value = "deadline", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp deadline;

    @TableField(value = "total_grade", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Integer totalGrade;

    @TableField(exist = false)
    private List<String> assignUrls;

    @TableField(value = "is_assignment", updateStrategy = FieldStrategy.NOT_EMPTY)
    private Boolean isAssignment;

    @TableField(value = "description", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String description;

    @TableField(value = "create_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp createTime;

    @TableField(value = "update_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp updateTime;

    public Assignment(String assignmentName, Long courseId, Timestamp deadline, Integer totalGrade, List<String> assignUrls, Boolean isAssignment, String description) {
        this.assignmentName = assignmentName;
        this.courseId = courseId;
        this.deadline = deadline;
        this.totalGrade = totalGrade;
        this.assignUrls = assignUrls;
        this.isAssignment = isAssignment;
        this.description = description;
    }

    /**
     * Any parameter if you don't know, just fill it with null. But assignmentName, courseName, teacherName is a must.
     *
     * @param assignmentName
     * @param courseName
     * @param teacherName
     * @param deadline
     * @param totalGrade
     * @param isAssignment
     * @param description
     */
    public Assignment(String assignmentName, String courseName, String teacherName, Timestamp deadline, Integer totalGrade,
                      Boolean isAssignment, String description, List<String> assignUrls) {
        this.assignmentName = assignmentName;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.deadline = deadline;
        this.totalGrade = totalGrade;
        this.isAssignment = isAssignment;
        this.description = description;
        this.assignUrls = assignUrls;
    }
}
