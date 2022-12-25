package com.example.live_video.controller;

import com.example.live_video.service.AssignmentService;
import com.example.live_video.service.CourseService;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/quiz")
public class QuizController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private AssignmentService assignmentService;


}
