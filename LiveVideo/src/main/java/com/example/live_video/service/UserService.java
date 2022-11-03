package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.User;
import com.example.live_video.exception.SQLUsernameConflictException;
import com.example.live_video.exception.SQLUserNotFoundException;

public interface UserService extends IService<User> {

    public Long getUserId(String username);

    /**
     * Register the user into database. If success, it will return true. If there's another user has the same name, it
     * will throw SQLRegisterUsernameConflictException
     * @param user
     * @return boolean
     * @throws SQLUsernameConflictException
     */
    public boolean register(User user) throws SQLUsernameConflictException;

    /**
     * Return true if the user has the same password with the record in database
     * @param user
     * @return boolean
     * @throws SQLUsernameConflictException
     */
    public boolean compareUserPassword(User user) throws SQLUserNotFoundException;

    public boolean removeUser(User user) throws SQLUserNotFoundException;
}
