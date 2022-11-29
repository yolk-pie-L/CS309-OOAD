package com.example.live_video.controller;

import com.example.live_video.dto.ExampleForm;
import com.example.live_video.dto.ExceptionMessage;
import com.example.live_video.dto.JSONObject;
import com.example.live_video.dto.UserForm;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.MyException;
import com.example.live_video.service.UserService;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@ResponseResult
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public void index() {
        User user = new User("shey", UserType.Administrator, "12012138@.com", "23", "phto_url", 0L);
        int a = 0;
        System.out.println("a" + a);
//        userService.register(user, );
    }

    @PostMapping("/api/register")
    public Boolean registerUser(@RequestBody @Valid UserForm userForm, BindingResult bindingResult) throws Exception {
        System.out.println(userForm);
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new MyException(list.get(0).getDefaultMessage());
        }
        return userService.register(userForm.convertToUser());
    }

    @PostMapping("/api/login")
    public String loginUser(@RequestBody UserForm userForm) throws Exception {
        return userService.login(userForm.convertToUser());
    }

    @PostMapping("/api/index")
    public Object hello(@Valid ExampleForm form, BindingResult bindingResult) throws Exception {
        if (form.getPassword().equals("helloworld"))
            bindingResult.addError(new ObjectError("This is a name", "This is default massage."));
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new MyException(list.get(0).getDefaultMessage());
        }
        return form;
    }

    @GetMapping("/api/user/all")
    public ModelAndView queryUserLikeUserName(@RequestParam String userName,
                                              @RequestParam String type) {
        ModelAndView modelAndView = new ModelAndView();
        // FIXME: 也许可以支持模糊搜索？（可以之后再说）
        modelAndView.addObject("userName", userName);
        modelAndView.addObject("photoUrl", "url");
        return modelAndView;
    }
}
