package com.example.live_video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName(value = "live_stream")
public class LiveStream {
    @TableId(type = IdType.AUTO)
    Long id;
    String title;

    @TableField(value = "user_id")
    Long userId;
    @TableField(exist = false)
    String userName;
    String url;

    public LiveStream(String title, String userName, String url) {
        this.title = title;
        this.userName = userName;
        this.url = url;
    }
}
