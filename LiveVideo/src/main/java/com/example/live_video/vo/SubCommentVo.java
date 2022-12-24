package com.example.live_video.vo;

import com.example.live_video.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Data
public class SubCommentVo {

    private String from;

    private Long fromId;

    private String fromHeadImg;

    private String to;

    private Long toId;

    private String comment;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp time;

    int commentNum;

    private int like;

    private Boolean inputShow;

    public SubCommentVo(String from, Long fromId, String fromHeadImg, String to, Long toId, String comment, Timestamp time, int commentNum, int like, Boolean inputShow) {
        this.from = from;
        this.fromId = fromId;
        this.fromHeadImg = fromHeadImg;
        this.to = to;
        this.toId = toId;
        this.comment = comment;
        this.time = time;
        this.commentNum = commentNum;
        this.like = like;
        this.inputShow = inputShow;
    }

    public static SubCommentVo parseComment(Comment comment){
        return new SubCommentVo(
                comment.getThisUser().getUserName(),
                comment.getUserId(),
                comment.getThisUser().getPhotoUrl(),
                comment.getParentUser().getUserName(),
                comment.getParentUser().getId(),
                comment.getContext(),
                comment.getCreateTime(),
                0,
                comment.getLikes(),
                false
        );
    }

    public static List<SubCommentVo> parseCommentList(List<Comment> commentList){
        List<SubCommentVo> subCommentVos = new LinkedList<>();
        for(Comment c: commentList){
            subCommentVos.add(SubCommentVo.parseComment(c));
        }
        return subCommentVos;
    }
}
