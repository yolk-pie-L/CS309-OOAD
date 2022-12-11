package com.example.live_video.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@TableName(value = "comment")
public class Comment {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "parent_id")
    private Long parentId;

    @TableField(value = "context")
    private String context;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "section_id")
    private Long sectionId;

    @TableField(value = "create_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp createTime;

    @TableField(exist = false)
    User thisUser;

    @TableField(exist = false)
    User parentUser;

    @TableField(exist = false)
    List<Comment> replyCommentList = new ArrayList<>();

    /*
    For non-root comment, parentId is the id you reply to.
     */
    public Comment(Long parentId, String context, Long userId, Long sectionId) {
        this.parentId = parentId;
        this.context = context;
        this.userId = userId;
        this.sectionId = sectionId;
    }

    /*
    For root comment
     */
    public Comment(String context, Long userId, Long sectionId) {
        this.parentId = -1L;
        this.context = context;
        this.userId = userId;
        this.sectionId = sectionId;
    }


}
