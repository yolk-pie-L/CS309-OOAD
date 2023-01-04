package com.example.live_video.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName(value = "user_order")
@NoArgsConstructor
@AllArgsConstructor
public class UserOrder {
    @TableId(value = "out_trade_no")
    String outTradeNo;
    @TableField(value = "username", updateStrategy = FieldStrategy.NEVER)
    String userName;
    StuOrderStatus status;

    public enum StuOrderStatus {
        APPROVED, FAILED, CREATED
    }
}