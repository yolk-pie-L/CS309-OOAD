package com.example.live_video.dto;

import lombok.Data;

@Data
public class CommentForm {
    Long sectionId;
    String userName;
    Long replyCommentId;
    String context;
}
