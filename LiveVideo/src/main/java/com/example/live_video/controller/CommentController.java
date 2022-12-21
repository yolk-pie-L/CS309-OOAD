package com.example.live_video.controller;

import com.example.live_video.entity.Comment;
import com.example.live_video.service.CommentService;
import com.example.live_video.service.StudentService;
import com.example.live_video.util.FileWithExcelUtil;
import com.example.live_video.vo.CommentVo;
import com.example.live_video.vo.StudentGradeVo;
import com.example.live_video.vo.StudentSectionGradeVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    StudentService studentService;

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
//
//    @PostMapping(value = "/exportExcel")
//    public void exportExcel(HttpServletResponse response) {
//        List<StudentGradeVo> activityList = studentService.getStudentGrades(1);
//        FileWithExcelUtil.exportExcel(activityList,"活动参加人员信息列表","sheet页名称", StudentGradeVo.class,"活动参加人员信息列表.xls",response);
//    }
}
