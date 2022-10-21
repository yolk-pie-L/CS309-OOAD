package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.User;
import com.example.live_video.exception.SQLUserNotFoundException;
import com.example.live_video.exception.SQLUsernameConflictException;
import com.example.live_video.mapper.UserMapper;
import com.example.live_video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * Register the user into database. If success, it will return true. If there's another user has the same name, it
     * will throw SQLRegisterUsernameConflictException
     * @param user
     * @return boolean
     * @throws SQLUsernameConflictException
     */
    @Override
    public boolean register(User user) throws SQLUsernameConflictException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        boolean existsFlag = userMapper.exists(queryWrapper);
        // 如果存在该用户，则抛出异常
        if(existsFlag){
            throw new SQLUsernameConflictException();
        }
        // 如果不存在该用户，则顺利执行插入
        int res = userMapper.insert(user);
        return res == 1;
    }

    /**
     * Return true if the user has the same password with the record in database
     * @param user
     * @return boolean
     * @throws SQLUsernameConflictException
     */
    @Override
    public boolean compareUserPassword(User user) throws SQLUsernameConflictException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.select("password");
        User resUser = userMapper.selectOne(queryWrapper);
        System.out.println(resUser);
        // 如果不存在该用户，则抛出异常
        if(resUser == null){
            throw new SQLUserNotFoundException();
        }
        return resUser.getPassword().equals(user.getPassword());
    }

    @Override
    public boolean removeUser(User user) throws SQLUserNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        User resUser = userMapper.selectOne(queryWrapper);
        // 如果不存在该用户，则抛出异常
        if(resUser == null){
            throw new SQLUserNotFoundException();
        }
        return userMapper.deleteById(resUser) == 1;
    }
}
