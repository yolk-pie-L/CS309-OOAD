package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.User;
import com.example.live_video.exception.SQLUsernameConflictException;
import com.example.live_video.exception.SQLUserNotFoundException;

public interface UserService extends IService<User> {
    public boolean register(User user) throws SQLUsernameConflictException;

    public boolean compareUserPassword(User user) throws SQLUserNotFoundException;

    public boolean removeUser(User user) throws SQLUserNotFoundException;
}
