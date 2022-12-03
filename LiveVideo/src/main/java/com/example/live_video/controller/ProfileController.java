package com.example.live_video.controller;

import com.example.live_video.dto.UserForm;
import com.example.live_video.service.UserService;
import com.example.live_video.vo.UserVo;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseResult
public class ProfileController {

    @Autowired
    UserService userService;

    @GetMapping("/api/user")
    public UserVo queryUserInfo(@RequestParam String userName) {
        return UserVo.convert(userService.getUser(userName));
    }

    @PostMapping("/api/user")
    public Boolean modifyUserInfo(@RequestBody UserForm userForm) {
        return userService.updateUser(userForm.convertToUser());
    }
}
