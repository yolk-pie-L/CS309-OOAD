package com.example.live_video.controller;

import com.example.live_video.dto.ExampleForm;
import com.example.live_video.dto.JSONObject;
import com.example.live_video.dto.UserForm;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.MyException;
import com.example.live_video.service.MailService;
import com.example.live_video.service.UserService;
import com.example.live_video.util.RandomUtils;
import com.example.live_video.util.TokenUtils;
import com.example.live_video.wrapper.ResponseResult;
import com.wf.captcha.utils.CaptchaUtil;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@ResponseResult
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;


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
        String verificationCode = RandomUtils.getVerificationCode();
        mailService.sendTextMailMessage(new String[]{userForm.getMail()}, "Verify Your Mail", verificationCode);
        // FIXME: compare the verification code in the userForm and verification code here，比较前端传入的验证码和此处的验证码是否相同
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            throw new MyException(list.get(0).getDefaultMessage());
        }
        return userService.register(userForm.convertToUser());
    }

    @PostMapping("/api/login")
    public JSONObject loginUser(@RequestBody UserForm userForm, HttpServletRequest request) throws Exception {
        JSONObject jsonObject = new JSONObject();
        System.out.println(userForm);
        if (!CaptchaUtil.ver(userForm.getCode(), request)) {
            CaptchaUtil.clear(request);  // 清除session中的验证码
            throw new MyException("验证码不正确");
        }
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
