package com.example.live_video.controller;

import com.example.live_video.dto.CommentForm;
import com.example.live_video.dto.DeleteCommentForm;
import com.example.live_video.entity.Comment;
import com.example.live_video.exception.MyException;
import com.example.live_video.service.CommentService;
import com.example.live_video.service.StudentService;
import com.example.live_video.service.UserService;
import com.example.live_video.vo.CommentVo;
import com.example.live_video.vo.StudentGradeVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import com.example.live_video.util.FileWithExcelUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @PostMapping("")
    public Long comment(@RequestBody CommentForm commentForm) {
        String userName = commentForm.getUserName();
        long replyCommentId = commentForm.getReplyCommentId();
        Long userId = userService.getUserId(userName);
        String context = commentForm.getContext();
        long sectionId = commentForm.getSectionId();
        Comment comment = new Comment(replyCommentId, context, userId, sectionId, 0);
        commentService.saveComment(comment);
        return comment.getId();
    }

    @GetMapping("/all/{sectionId}")
    public Long getTotal(@PathVariable Long sectionId) {
        return commentService.countComments(sectionId);
    }

    @GetMapping("/{sectionId}")
    public List<CommentVo> getCommentList(@PathVariable Long sectionId) {
        return CommentVo.parseCommentList(commentService.getCommentList(sectionId));
    }

    @PostMapping("/del")
    public boolean deleteComment(@RequestBody DeleteCommentForm deleteCommentForm) throws MyException {
        long commentId = deleteCommentForm.getCommentId();
        long userId = userService.getUserId(deleteCommentForm.getUserName());
        return commentService.deleteComment(commentId, userId);
    }

    @PostMapping("/update")
    public boolean updateComment(@RequestParam long commentId, @RequestParam int likes) {
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setLikes(likes);
        return commentService.updateComment(comment);
    }
}
