package com.example.live_video.controller;

import com.example.live_video.dto.ExceptionMessage;
import com.example.live_video.dto.UserForm;
import com.example.live_video.entity.Course;
import com.example.live_video.entity.User;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.UserService;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ResponseResult
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @GetMapping("/api/student")
    public Object queryStudentInfo(@RequestParam String userName) {
        User user = userService.getUserByUsername(userName);
        List<Course> courseList = courseService.getAttendedCourseOfUser(userName);
        return null;
    }

    @GetMapping("/api/teacher")
    public UserForm queryTeacherInfo(@RequestParam String userName) {
        User user = userService.getUserByUsername(userName);
        List<Course> courseList = courseService.getApprovedCourses(0, 0);
        return null;
    }

    @PostMapping("/api/person")
    public ExceptionMessage modifyPersonalInfo(@RequestParam String userName,
                                               @RequestParam String password,
                                               @RequestParam String newPassword,
                                               @RequestParam String photoUrl) throws Exception{
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        Object result = userService.compareUserPassword(user);
        if (result instanceof Exception) {
            userService.updateUserInfo(userName, newPassword, photoUrl);
        }
        return null;
    }
}
