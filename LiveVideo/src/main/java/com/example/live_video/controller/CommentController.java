package com.example.live_video.controller;

import com.example.live_video.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

public class CommentController {

    @Autowired
    CommentService commentService;
}
