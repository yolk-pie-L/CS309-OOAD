package com.example.live_video.controller;

import com.example.live_video.entity.User;
import com.example.live_video.exception.SQLUsernameConflictException;
import com.example.live_video.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired(required = false)
    private UserServiceImpl userService;


    @GetMapping("/")
    public void index() throws SQLUsernameConflictException {
        User user = new User("szz", 'A', "12012138@.com", "23", "phto_url", 0L);
        int a = 0;
        System.out.println("a" + a);
        userService.register(user);
    }

}
