package com.example.live_video.vo;

import lombok.Data;

@Data
public class SubCommentVo {

    private String from;

    private Long fromId;

    private String fromHeadImg;

    private String to;

    private String toId;

    private String comment;

    private Long commentNum;

    private Long like;

    private Boolean inputShow;
}
