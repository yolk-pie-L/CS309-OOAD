package com.example.live_video.service;

import com.example.live_video.entity.Course;

import java.util.List;

public interface StudentService {

    public Object enrollCourse(String teacherName, String courseName, String studentName);

    public Boolean exitCourse(String teacherName, String courseName, String studentName);

    public List<Course> getEnrolledCourses(String studentName);

    public Boolean setStudentAssignGrade(String studentName, String courseName, String teacherName, String assignName, int grade);

    public Boolean submitAssignment(String studentName, String courseName, String teacherName, String assignName, List<String> assignUrls);

    public List<String> getStudentAssignmentUrls(String studentName, String courseName, String teacherName, String assignName);

    public Boolean resubmitAssignment(String studentName, String courseName, String teacherName, String assignName, List<String> assignUrls);
}
