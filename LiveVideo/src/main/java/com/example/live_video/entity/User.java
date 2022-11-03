package com.example.live_video.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@TableName(value = "user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "username")
    private String userName;

    @TableField(value = "type")
    private UserType userType;
    private String mail;
    private String password;

    @TableField(value = "photo_url")
    private String photoUrl;
    private Long account;

    @TableField(value = "create_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp createTime;

    @TableField(value = "update_time", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Timestamp updateTime;

    @TableLogic(value = "0", delval = "id")
    @TableField(value = "is_delete", insertStrategy = FieldStrategy.NOT_EMPTY)
    private Integer deleteFlag;

    public User(String userName, UserType userType, String mail, String password, String photoUrl, Long account) {
        this.userName = userName;
        this.userType = userType;
        this.mail = mail;
        this.password = password;
        this.photoUrl = photoUrl;
        this.account = account;
    }

    public User(String userName, UserType userType, String mail, String password) {
        this(userName, userType, mail, password, null, 0L);
    }
}
