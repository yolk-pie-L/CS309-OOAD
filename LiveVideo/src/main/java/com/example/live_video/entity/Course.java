package com.example.live_video.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@TableName(value = "course")
public class Course {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "course_name")
    private String courseName;

    @TableField(value = "user_id")
    private Long teacherId;

    @TableField(exist = false)
    private String teacherName;

    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String tag;

    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private Long charge;

    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String description;

    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private CourseStatus status;

    @TableField(value = "picture_url", updateStrategy = FieldStrategy.NOT_EMPTY)
    private String pictureUrl;


    @TableField(value = "create_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp createTime;

    @TableField(value = "update_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp updateTime;

    @TableLogic(value = "0", delval = "id")
    @TableField(value = "is_delete", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Integer deleteFlag;

    /**
     * <p>
     * For upper layer. If you're not LiXin, you should use this constructor because you don't know the teacher_id.
     * If you don't know the exact parameter, for example, you don't know charge, but you just want to update
     * tag, you can just set charge to null, description to null.
     * It's essential to fill courseName and teacherName two fields.
     * <p/>
     * @param courseName
     * @param teacherName
     * @param tag
     * @param charge
     * @param description
     * @param status
     * @param pictureUrl
     */
    public Course(@NotNull String courseName, @NotNull String teacherName, String tag, Long charge, String description, CourseStatus status, String pictureUrl) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.tag = tag;
        this.charge = charge;
        this.description = description;
        this.status = status;
        this.pictureUrl = pictureUrl;
    }

    /**
     * For database layer
     * @param courseName
     * @param teacherId
     * @param tag
     * @param charge
     * @param description
     * @param status
     * @param pictureUrl
     */
    public Course(String courseName, Long teacherId, String tag, Long charge, String description, CourseStatus status, String pictureUrl) {
        this.courseName = courseName;
        this.teacherId = teacherId;
        this.tag = tag;
        this.charge = charge;
        this.description = description;
        this.status = status;
        this.pictureUrl = pictureUrl;
    }

}
