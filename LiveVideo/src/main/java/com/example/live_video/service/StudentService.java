package com.example.live_video.service;

import com.example.live_video.entity.Course;

import java.util.List;

public interface StudentService {

    public Boolean enrollCourse(String teacherName, String courseName, String studentName);

    public Boolean exitCourse(String teacherName, String courseName, String studentName);

    public List<Course> getEnrolledCourse(String studentName);
}
