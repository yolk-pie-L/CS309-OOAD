package com.example.live_video.service.impl;

import com.example.live_video.entity.Course;
import com.example.live_video.entity.User;
import com.example.live_video.exception.EnrollCourseException;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.StudentMapper;
import com.example.live_video.mapper.UserMapper;
import com.example.live_video.service.AssignmentService;
import com.example.live_video.service.StudentService;
import com.example.live_video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    UserService userService;

    @Autowired
    AssignmentService assignmentService;


    @Override
    @Transactional
    public Boolean enrollCourse(String teacherName, String courseName, String studentName) throws EnrollCourseException {
        Long courseId = courseMapper.getCourseIdByTeacherNameCourseName(teacherName, courseName);
        Long studentId = userService.getUserId(studentName);
        Long courseCharge = courseMapper.getCourseChargeByTeacherNameCourseName(teacherName, courseName);
        Long studentAccount = userService.getUserAccount(studentName);
        if (courseCharge <= studentAccount) {
            User user = new User(studentName, null, null, null, null,
                    studentAccount - courseCharge);
            userService.updateUser(user);
            studentMapper.enrollCourse(studentId, courseId);
            return true;
        }
        throw new EnrollCourseException("student account not enough");
    }

    @Override
    public Boolean exitCourse(String teacherName, String courseName, String studentName) {
        Long courseId = courseMapper.getCourseIdByTeacherNameCourseName(teacherName, courseName);
        Long studentId = userService.getUserId(studentName);
        studentMapper.exitCourse(studentId, courseId);
        return true;
    }

    @Override
    public List<Course> getEnrolledCourseList(String studentName) {
        Long studentId = userService.getUserId(studentName);
        List<Course> courses = studentMapper.getEnrolledCourses(studentId);
        return courses;
    }

    @Override
    public Boolean setStudentAssignGrade(String studentName, String courseName, String teacherName, String assignName, int grade) {
        Long assignId = assignmentService.getAssignmentId(courseName, teacherName, assignName);
        Long studentId = userService.getUserId(studentName);
        studentMapper.setStudentAssignGrade(studentId, assignId, grade);
        return true;
    }

    @Override
    @Transactional
    public Boolean submitAssignment(String studentName, String courseName, String teacherName, String assignName, List<String> assignUrls) {
        Long assignId = assignmentService.getAssignmentId(courseName, teacherName, assignName);
        Long studentId = userService.getUserId(studentName);
        for (String assignUrl : assignUrls) {
            studentMapper.submitAssignment(studentId, assignId, assignUrl);
        }
        return true;
    }

    @Override
    public List<String> getStudentAssignmentUrlList(String studentName, String courseName, String teacherName, String assignName) {
        Long assignId = assignmentService.getAssignmentId(courseName, teacherName, assignName);
        Long studentId = userService.getUserId(studentName);
        List<String> assignUrls = studentMapper.getStudentSubmittedAssignUrls(studentId, assignId);
        return assignUrls;
    }

    @Override
    public Boolean resubmitAssignment(String studentName, String courseName, String teacherName, String assignName, List<String> assignUrls) {
        Long assignId = assignmentService.getAssignmentId(courseName, teacherName, assignName);
        Long studentId = userService.getUserId(studentName);
        studentMapper.deleteStudentAssignment(studentId, assignId);
        return this.submitAssignment(studentName, courseName, teacherName, assignName, assignUrls);
    }
}
