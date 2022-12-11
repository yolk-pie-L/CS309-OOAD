package com.example.live_video.dto;

import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import lombok.Data;

@Data
public class CourseDto {
    private String courseName;

    private String teacherName;

    private String tag;

    private Long charge;

    private String description;

    private String pictureUrl;

    public Course convertToCourse(CourseStatus status) {
        return new Course(
                courseName,
                teacherName,
                tag,
                charge,
                description,
                status,
                pictureUrl
        );
    }
}
