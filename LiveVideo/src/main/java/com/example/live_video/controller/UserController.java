package com.example.live_video.controller;

import com.example.live_video.dto.ExampleForm;
import com.example.live_video.dto.JSONObject;
import com.example.live_video.dto.UserForm;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.MyException;
import com.example.live_video.service.UserService;
import com.example.live_video.util.TokenUtils;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public Boolean registerUser(@RequestBody @Valid UserForm userForm, BindingResult bindingResult) throws Exception {
        if (!userForm.getPassword().equals(userForm.getRepeatPassword()))
            throw new MyException("密码和重复密码不一致");
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new MyException(list.get(0).getDefaultMessage());
        }
        return userService.register(userForm.convertToUser());
    }

    @PostMapping("/api/login")
    public JSONObject loginUser(@RequestBody UserForm userForm) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if (userService.compareUserPassword(userForm.getUserName(), userForm.getPassword())) {
            jsonObject.put("token", TokenUtils.sign(userForm.getUserName()));
            return jsonObject;
        }else
            throw new MyException("用户名或密码错误");
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
    public JSONObject queryUserLikeUserName(@RequestParam String userName,
                                              @RequestParam String type) {
        JSONObject jsonObject = new JSONObject();
        // FIXME: 也许可以支持模糊搜索？（可以之后再说）
        jsonObject.put("userName", userName);
        jsonObject.put("photoUrl", "url");
        return jsonObject;
    }
}
