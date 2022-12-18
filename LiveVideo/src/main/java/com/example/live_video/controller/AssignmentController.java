package com.example.live_video.controller;

import com.example.live_video.entity.Assignment;
import com.example.live_video.service.AssignmentService;
import com.example.live_video.service.CourseService;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/assignment")
public class AssignmentController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/allAssignment?courseId={courseId}")
    public Assignment queryAssignmentByCourseName(@PathVariable String courseId) {
        assignmentService.getAssignmentByCourse("","");
        return null;
    }
}
