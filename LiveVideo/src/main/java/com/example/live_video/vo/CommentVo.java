package com.example.live_video.vo;

import com.example.live_video.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Data
public class CommentVo {

    private String name;

    private Long Id;

    private String headImg;

    private String comment;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp time;

    private int like;

    private int commentNum;

    private Boolean inputShow;

    private List<SubCommentVo> reply;

    public CommentVo(String name, Long id, String headImg, String comment, Timestamp time, List<SubCommentVo> reply, int like, int commentNum, Boolean inputShow) {
        this.name = name;
        Id = id;
        this.headImg = headImg;
        this.comment = comment;
        this.time = time;
        this.reply = reply;
        this.like = like;
        this.commentNum = commentNum;
        this.inputShow = inputShow;
    }

    public static CommentVo parseCommentVo(Comment comment){
        return new CommentVo(
                comment.getThisUser().getUserName(),
                comment.getId(),
                comment.getThisUser().getPhotoUrl(),
                comment.getContext(),
                comment.getCreateTime(),
                SubCommentVo.parseCommentList(comment.getReplyCommentList()),
                comment.getLikes(),
                comment.getReplyCommentList().size(),
                false
        );
    }

    public static List<CommentVo> parseCommentList(List<Comment> commentList){
        List<CommentVo> commentVoList = new LinkedList<>();
        for(Comment c: commentList){
            commentVoList.add(parseCommentVo(c));
        }
        return commentVoList;
    }
}
