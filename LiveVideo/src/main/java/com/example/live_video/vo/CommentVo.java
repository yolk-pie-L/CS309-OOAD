package com.example.live_video.vo;

import com.example.live_video.entity.Comment;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class CommentVo {

    private String name;

    private Long Id;

    private String headImg;

    private String comment;

    private List<SubCommentVo> reply;

    private int like;

    private int commentNum;

    private Boolean inputShow;

    public CommentVo(String name, Long id, String headImg, String comment, List<SubCommentVo> reply, int like, int commentNum, Boolean inputShow) {
        this.name = name;
        Id = id;
        this.headImg = headImg;
        this.comment = comment;
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
