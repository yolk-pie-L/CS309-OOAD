package com.example.live_video.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVo {

    private String name;

    private Long Id;

    private String headImg;

    private String comment;

    private List<SubCommentVo> reply;

    private Long like;

    private Long commentNum;

    private Boolean inputShow;
}
