package com.example.live_video.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName(value = "user_order")
@NoArgsConstructor
@AllArgsConstructor
public class UserOrder {
    @TableField(value = "out_trade_no")
    String outTradeNo;
    @TableField(value = "username")
    String userName;
    StuOrderStatus status;
    enum StuOrderStatus{
        APPROVED, FAILED, CREATED
    }
}