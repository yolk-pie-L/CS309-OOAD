package com.example.live_video.controller;

import com.example.live_video.service.MyWebSocket;
import javafx.scene.input.MouseDragEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 弹幕主页Controller层
 */
@Controller
@RequestMapping("/liveroom")
public class DanmuController {

    @Autowired
    private MyWebSocket myWebSocket;

    @GetMapping("/hello")
    public String SayHello(){
        String msg = "Hello";
        return msg;
    }

    @GetMapping("/index")
    public ModelAndView index(){
        System.out.println("index");
        return new ModelAndView("miaomiao");
    }

//    @GetMapping("/send")
//    public void send(){
//        String msg = "Hello EveryOne!";
//        myWebSocket.sendMessage(msg);
//    }

}
