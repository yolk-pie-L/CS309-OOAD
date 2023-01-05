package com.example.live_video.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class LiveStreamForm {
    String title;
    String description;
    String userName;
}
