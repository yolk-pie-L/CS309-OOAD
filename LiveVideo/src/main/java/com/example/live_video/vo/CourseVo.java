package com.example.live_video.vo;

import com.example.live_video.entity.Course;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseVo {

    private String courseName;

    private String teacherName;

    private String introduction;

    private String coursePicture;

    private String privateKeyUrl;

    private String status;

    public CourseVo(String courseName, String teacherName, String introduction, String coursePicture, String privateKeyUrl, String status) {
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.introduction = introduction;
        this.coursePicture = coursePicture;
        this.privateKeyUrl = privateKeyUrl;
        this.status = status;
    }

    public CourseVo(String courseName, String privateKeyUrl, String status) {
        this.courseName = courseName;
        this.privateKeyUrl = privateKeyUrl;
        this.status = status;
    }

    public static List<CourseVo> parse(List<Course> courseList) {
        List<CourseVo> courseVoList = new ArrayList<>();
        courseList.forEach(course -> {
            courseVoList.add(CourseVo.parse(course));
        });
        return courseVoList;
    }

    public static CourseVo parse(Course course) {
        return new CourseVo(
                course.getCourseName(),
                course.getTeacherName(),
                course.getDescription(),
                course.getPictureUrl(),
                null, //FIXME: course.getPrivateKeyUrl()
                course.getStatus().toString()
                );
    }

    public static CourseVo parseEasy(Course course) {
        return new CourseVo(
                course.getCourseName(),
                course.getPictureUrl(),
                course.getStatus().toString()
        );
    }
}
