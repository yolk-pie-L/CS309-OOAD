package com.example.live_video.service;

import com.example.live_video.entity.Course;
import com.example.live_video.exception.EnrollCourseException;

import java.util.List;

public interface StudentService {

    public Boolean enrollCourse(Long courseId, String studentName) throws EnrollCourseException;

    public Boolean exitCourse(Long courseId, String studentName);

    public List<Course> getEnrolledCourseList(String studentName);

    public Boolean setStudentAssignGrade(String studentName, Long assignId, int grade);

    public int getStudentAssignGrade(String studentName, Long assignId);

    public Boolean submitAssignment(String studentName, Long assignId, List<String> assignUrls);

    public List<String> getStudentAssignmentUrlList(String studentName, Long assignId);

    public Boolean resubmitAssignment(String studentName, Long assignId, List<String> assignUrls);
}
