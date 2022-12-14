package com.example.live_video.controller;

import com.example.live_video.entity.Comment;
import com.example.live_video.service.CommentService;
import com.example.live_video.vo.CommentVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/")
    public Boolean comment(@RequestParam String userName,
                           @RequestParam String replyUserName,
                           @RequestParam String context,
                           @RequestParam Date date) {
        Comment comment = new Comment();
        commentService.saveComment(comment);
        return true;
    }

    @GetMapping("/all/{sectionId}")
    public Long getTotal(@PathVariable Long sectionId) {
        return (long) commentService.getCommentList(sectionId).size();
    }

    @GetMapping("/{sectionId}")
    public CommentVo getCommentList(@PathVariable Long sectionId) {
//        return commentService.getCommentList(sectionId);
        return null;
    }
}
