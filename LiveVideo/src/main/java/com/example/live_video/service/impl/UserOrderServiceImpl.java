package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.UserOrder;
import com.example.live_video.mapper.UserOrderMapper;
import com.example.live_video.service.UserOrderService;
import org.springframework.stereotype.Service;

@Service
public class UserOrderServiceImpl extends ServiceImpl<UserOrderMapper, UserOrder> implements UserOrderService {
}
