package com.example.live_video.service;

import com.example.live_video.entity.Course;
import com.example.live_video.exception.EnrollCourseException;

import java.util.List;

public interface StudentService {

    public Boolean enrollCourse(String teacherName, String courseName, String studentName) throws EnrollCourseException;

    public Boolean exitCourse(String teacherName, String courseName, String studentName);

    public List<Course> getEnrolledCourseList(String studentName);

    public Boolean setStudentAssignGrade(String studentName, String courseName, String teacherName, String assignName, int grade);

    public Boolean submitAssignment(String studentName, String courseName, String teacherName, String assignName, List<String> assignUrls);

    public List<String> getStudentAssignmentUrlList(String studentName, String courseName, String teacherName, String assignName);

    public Boolean resubmitAssignment(String studentName, String courseName, String teacherName, String assignName, List<String> assignUrls);
}
