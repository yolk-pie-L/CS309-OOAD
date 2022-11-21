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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    public Object registerUser(@Valid UserForm userForm, BindingResult bindingResult) {
        JSONObject result = new JSONObject();
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return new MyException(list.get(0).getDefaultMessage());
        } else {
            Object obj = userService.register(userForm.convertToUser());
            if (obj instanceof MyException) {
                result.put("msg", ((MyException) obj).getMessage());
            }
        }
        return result;
    }

    @PostMapping("/api/login")
    public ExceptionMessage loginUser(UserForm userForm) {
        try {
            String type = userService.login(userForm.convertToUser());
            return new ExceptionMessage("OK", type, null);
        } catch (MyException e) {
            return new ExceptionMessage("error", e.getMessage());
        }
    }

    @PostMapping("/api/index")
    public Object hello(@Valid ExampleForm form, BindingResult bindingResult) {
        if (form.getPassword().equals("helloworld"))
            bindingResult.addError(new ObjectError("This is a name", "This is default massage."));
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            return new MyException(list.get(0).getDefaultMessage());
        }
        return form;
    }
}
