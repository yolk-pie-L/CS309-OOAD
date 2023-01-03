package com.example.live_video.dto;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.live_video.entity.UserOrder;
import lombok.Data;

@Data
public class UserOrderForm {
    String outTradeNo;
    String status;
}
