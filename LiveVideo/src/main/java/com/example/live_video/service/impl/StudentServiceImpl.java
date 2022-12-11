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
    public Boolean enrollCourse(Long courseId, String studentName) throws EnrollCourseException {
        Long studentId = userService.getUserId(studentName);
        Long courseCharge = courseMapper.getCourseCharge(courseId);
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
    public Boolean exitCourse(Long courseId, String studentName) {
        Long studentId = userService.getUserId(studentName);
        studentMapper.exitCourse(studentId, courseId);
        return true;
    }

    @Override
    public List<Course> getEnrolledCourseList(String studentName) {
        Long studentId = userService.getUserId(studentName);
        List<Course> courses = studentMapper.getEnrolledCourseList(studentId);
        return courses;
    }

    @Override
    public Boolean setStudentAssignGrade(String studentName, Long assignId, int grade) {
        Long studentId = userService.getUserId(studentName);
        studentMapper.setStudentAssignGrade(studentId, assignId, grade);
        return true;
    }

    @Override
    public int getStudentAssignGrade(String studentName, Long assignId) {
        Long studentId = userService.getUserId(studentName);
        return studentMapper.getStudentAssignGrade(studentId, assignId);
    }

    @Override
    @Transactional
    public Boolean submitAssignment(String studentName, Long assignId, List<String> assignUrls) {
        Long studentId = userService.getUserId(studentName);
        for (String assignUrl : assignUrls) {
            studentMapper.submitAssignment(studentId, assignId, assignUrl);
        }
        return true;
    }

    @Override
    public List<String> getStudentAssignmentUrlList(String studentName, Long assignId) {
        Long studentId = userService.getUserId(studentName);
        List<String> assignUrls = studentMapper.getStudentSubmittedAssignUrlList(studentId, assignId);
        return assignUrls;
    }

    @Override
    @Transactional
    public Boolean resubmitAssignment(String studentName, Long assignId, List<String> assignUrls) {
        Long studentId = userService.getUserId(studentName);
        studentMapper.deleteStudentAssignment(studentId, assignId);
        return this.submitAssignment(studentName, assignId, assignUrls);
    }
}
