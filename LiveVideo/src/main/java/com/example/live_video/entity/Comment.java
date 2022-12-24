package com.example.live_video.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@TableName(value = "comment")
public class Comment {

    /*
    this id of comment
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /*
    所回复的评论的id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /*
    评论内容
     */
    @TableField(value = "context")
    private String context;

    /*
    发表评论的用户
     */
    @TableField(value = "user_id")
    private Long userId;

    /*
    所属于的章节
     */
    @TableField(value = "section_id")
    private Long sectionId;

    int likes;

    /*
    创建评论的时间
     */
    @TableField(value = "create_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp createTime;

    /*
    发表评论的用户
     */
    @TableField(exist = false)
    User thisUser;

    /*
    被回复的用户
     */
    @TableField(exist = false)
    User parentUser;

    /*
    如果这是根节点，replyCommentList就不为空，如果不是根节点，该field为空
    对于根节点，该field表示其下的所有评论
     */
    @TableField(exist = false)
    List<Comment> replyCommentList = new ArrayList<>();

    /*
    For non-root comment, parentId is the id you reply to.
     */
    public Comment(Long parentId, String context, Long userId, Long sectionId, int likes) {
        this.parentId = parentId;
        this.context = context;
        this.userId = userId;
        this.sectionId = sectionId;
        this.likes = likes;
    }

    /*
    For root comment
     */
    public Comment(String context, Long userId, Long sectionId, int likes) {
        this.parentId = -1L;
        this.context = context;
        this.userId = userId;
        this.sectionId = sectionId;
        this.likes = likes;
    }


}
