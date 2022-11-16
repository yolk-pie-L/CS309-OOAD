package com.example.live_video.service;

import com.example.live_video.entity.Course;
import com.example.live_video.entity.User;

public interface StudentService {

    public Boolean enrollCourse(String teacherName, String courseName, String studentName);

    public void exitCourse(Course course, User student);

    public void getEnrolledCourse(String studentName);
}
