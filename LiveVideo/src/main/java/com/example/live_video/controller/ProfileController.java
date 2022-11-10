package com.example.live_video.controller;

import com.example.live_video.dto.ExceptionMessage;
import com.example.live_video.dto.UserForm;
import com.example.live_video.entity.User;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @GetMapping("/api/student")
    public UserForm queryStudentInfo(@RequestParam String userName) {
        return null;
    }

    @GetMapping("/api/teacher")
    public UserForm queryTeacherInfo(@RequestParam String userName) {
        return null;
    }

    @PostMapping("/api/person")
    public ExceptionMessage modifyPersonalInfo(@RequestParam String userName,
                                               @RequestParam String password,
                                               @RequestParam String newPassword,
                                               @RequestParam String photoUrl) {
        return null;
    }
}
