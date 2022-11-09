package com.example.live_video.controller;

import com.example.live_video.dto.ExceptionMessage;
import com.example.live_video.dto.UserForm;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.MyException;
import com.example.live_video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public void index() {
        User user = new User("jkljlk", UserType.Administrator, "12013029@.com", "23", "phto_url", 0L);
        int a = 0;
        System.out.println("a" + a);
        try {
            userService.register(user);
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/register")
    public ExceptionMessage registerUser(UserForm userForm) {
        System.out.println(userForm);
        try {
            userService.register(userForm.convertToUser());
        } catch (MyException e) {
            return new ExceptionMessage("error", e.getMessage());
        }
        return new ExceptionMessage("OK");
    }
}
