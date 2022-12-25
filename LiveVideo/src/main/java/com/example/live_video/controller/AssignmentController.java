package com.example.live_video.controller;

import com.example.live_video.entity.Assignment;
import com.example.live_video.entity.Course;
import com.example.live_video.service.AssignmentService;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.UserService;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/assignment")
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/allAssignment")
    public List<Assignment> queryAssignmentByCourseName(@RequestParam("courseId") String courseId) {
        Course course = courseService.getOneCourse(Long.parseLong(courseId));
        String courseName = course.getCourseName();
        String teacherName = course.getTeacherName();
        if (teacherName == null) teacherName = userService.getById(course.getTeacherId()).getUserName();
        List<Assignment> assignmentList = assignmentService.getAssignmentByCourse(courseName, teacherName);
        return assignmentList;
    }
}
