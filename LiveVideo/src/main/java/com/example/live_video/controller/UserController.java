package com.example.live_video.controller;

import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.SQLMailConflictException;
import com.example.live_video.exception.SQLUsernameConflictException;
import com.example.live_video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public void index() throws SQLUsernameConflictException, SQLMailConflictException {
        User user = new User("shey", UserType.Administrator, "12012138@.com", "23");
        int a = 0;
        System.out.println("a" + a);
        userService.register(user);
    }

}
