package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.User;
import com.example.live_video.exception.MyException;
import com.example.live_video.exception.SQLMailConflictException;
import com.example.live_video.exception.SQLUserNotFoundException;
import com.example.live_video.exception.SQLUsernameConflictException;
import com.example.live_video.mapper.UserMapper;
import com.example.live_video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired(required = false)
    UserMapper userMapper;

    @Override
    public boolean register(User user) throws MyException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUserName());
        boolean existsFlag = userMapper.exists(queryWrapper);
        // 如果存在该用户名，则抛出异常
        if(existsFlag){
            throw new SQLUsernameConflictException();
        }
        queryWrapper.clear();

        queryWrapper.eq("mail", user.getMail());
        existsFlag = userMapper.exists(queryWrapper);
        if(existsFlag){
            throw new SQLMailConflictException();
        }
        // 如果不存在该用户，则顺利执行插入
        int res = userMapper.insert(user);
        return res == 1;
    }


    @Override
    public boolean compareUserPassword(User user) throws MyException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUserName());
        queryWrapper.select("password");
        User resUser = userMapper.selectOne(queryWrapper);
        // 如果不存在该用户，则抛出异常
        if(resUser == null){
            throw new SQLUserNotFoundException();
        }
        return resUser.getPassword().equals(user.getPassword());
    }

    @Override
    public boolean removeUser(User user) throws SQLUserNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUserName());
        User resUser = userMapper.selectOne(queryWrapper);
        // 如果不存在该用户，则抛出异常
        if(resUser == null){
            throw new SQLUserNotFoundException();
        }
        return userMapper.deleteById(resUser) == 1;
    }
}
