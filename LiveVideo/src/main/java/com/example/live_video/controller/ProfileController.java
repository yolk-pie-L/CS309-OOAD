package com.example.live_video.controller;

import com.example.live_video.dto.UserForm;
import com.example.live_video.entity.Course;
import com.example.live_video.entity.User;
import com.example.live_video.exception.MyException;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.StudentService;
import com.example.live_video.service.UserService;
import com.example.live_video.util.TokenUtils;
import com.example.live_video.vo.UserVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import com.example.live_video.wrapper.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@ResponseResult
@UserLoginToken
public class ProfileController {

    @Autowired
    UserService userService;

    @GetMapping("/api/user")
    public UserVo queryUserInfo(@RequestHeader String token) throws Exception{
        return UserVo.convert(userService.getUser(TokenUtils.getUserName(token)));
    }

    @PostMapping("/api/user")
    public Boolean modifyUserInfo(@RequestBody @Valid UserForm userForm, @RequestParam User user, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new MyException(list.get(0).getDefaultMessage());
        }
        if (!userForm.getPassword().equals(user.getPassword())) {
            throw new MyException("密码不一致");
        }
        if (userForm.getUserName().equals(user.getUserName())) {
            user.setMail(userForm.getMail());
            user.setPhotoUrl(userForm.getPhotoUrl());
            if (StringUtils.hasText(userForm.getRepeatPassword()))
                user.setPassword(userForm.getRepeatPassword());
        }
        return userService.updateUser(userForm.convertToUser());
    }
}
