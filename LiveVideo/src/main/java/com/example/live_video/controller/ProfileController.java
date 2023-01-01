package com.example.live_video.controller;

import com.example.live_video.dto.UserForm;
import com.example.live_video.entity.User;
import com.example.live_video.exception.MyException;
import com.example.live_video.service.UserService;
import com.example.live_video.util.MailUtil;
import com.example.live_video.util.TokenUtils;
import com.example.live_video.vo.UserVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import com.example.live_video.wrapper.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
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
        return UserVo.parse(userService.getUser(TokenUtils.getUserName(token)));
    }

    @PostMapping("/api/user")
    public Boolean modifyUserInfo(@RequestHeader String token, @RequestBody UserForm userForm, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new MyException(list.get(0).getDefaultMessage());
        }
        if (!MailUtil.ver(userForm.getCode()))
            throw new MyException("邮箱验证码不一致");
        User user = userService.getUser(TokenUtils.getUserName(token));
        if (!userForm.getPassword().equals(userForm.getRepeatPassword())) {
            throw new MyException("密码不一致");
        }
        user.setMail(userForm.getMail());
        user.setPhotoUrl(userForm.getPhotoUrl());
        if (StringUtils.hasText(userForm.getRepeatPassword()))
            user.setPassword(userForm.getRepeatPassword());
        return userService.updateUser(user);
    }

    @PostMapping("/api/user/avatar")
    public Boolean modifyUserAvatar(@RequestHeader String token, @RequestBody UserForm userForm) throws Exception {
        if (TokenUtils.getUserName(token).equals(userForm.getUserName())) {
            User user = userService.getUser(userForm.getUserName());
            user.setPhotoUrl(userForm.getPhotoUrl());
            userService.updateUser(user);
        } else {
            throw new MyException("用户名错误");
        }
        return true;
    }
}
