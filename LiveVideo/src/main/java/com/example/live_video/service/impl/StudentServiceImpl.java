package com.example.live_video.service.impl;

import com.example.live_video.entity.Course;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.StudentMapper;
import com.example.live_video.service.StudentService;
import com.example.live_video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    UserService userService;

    @Override
    public Boolean enrollCourse(String teacherName, String courseName, String studentName) {
        Long courseId = courseMapper.getCourseIdByTeacherNameCourseName(teacherName, courseName);
        Long studentId = userService.getUserIdByUsername(studentName);
        studentMapper.enrollCourse(studentId, courseId);
        return true;
    }

    @Override
    public Boolean exitCourse(String teacherName, String courseName, String studentName) {
        Long courseId = courseMapper.getCourseIdByTeacherNameCourseName(teacherName, courseName);
        Long studentId = userService.getUserIdByUsername(studentName);
        studentMapper.exitCourse(studentId, courseId);
        return true;
    }

    @Override
    public List<Course> getEnrolledCourse(String studentName) {
        Long studentId = userService.getUserIdByUsername(studentName);
        List<Course> courses = studentMapper.getEnrolledCourses(studentId);
        return courses;
    }
}
