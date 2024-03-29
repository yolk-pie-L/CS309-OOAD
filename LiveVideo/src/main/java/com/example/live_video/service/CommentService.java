package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.Comment;
import com.example.live_video.exception.MyException;

import java.util.List;

public interface CommentService extends IService<Comment> {

    List<Comment> getCommentList(Long sectionId);

    boolean saveComment(Comment comment);

    boolean deleteComment(Long commentId, Long userId) throws MyException;

    Comment getOneComment(Long commentId);

    long countComments(Long sectionId);

    boolean updateComment(Comment comment);
}
