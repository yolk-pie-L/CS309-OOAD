package com.example.live_video.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Insert;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@TableName(value = "user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;
    private Character type;
    private String mail;
    private String password;

    @TableField(value = "photo_url", select = false)
    private String photoUrl;
    private Long account;

    @TableField(value = "create_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp createTime;

    @TableField(value = "update_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp updateTime;

    @TableLogic(value = "0", delval = "id")
    @TableField(value = "is_delete", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Integer deleteFlag;

    public User(String username, Character type, String mail, String password, String photoUrl, Long account) {
        this.username = username;
        this.type = type;
        this.mail = mail;
        this.password = password;
        this.photoUrl = photoUrl;
        this.account = account;
    }
}
