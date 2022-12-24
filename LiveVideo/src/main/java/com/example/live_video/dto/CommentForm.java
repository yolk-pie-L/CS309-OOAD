package com.example.live_video.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentForm {
    Long sectionId;
    String userName;
    Long replyCommentId;
    String context;
}
