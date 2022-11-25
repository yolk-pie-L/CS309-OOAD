package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
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
    public Boolean register(User user) throws MyException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", user.getUserName());
        boolean existsFlag = userMapper.exists(userQueryWrapper);
        // 如果存在该用户名，则抛出异常
        if(existsFlag){
            throw new SQLUsernameConflictException();
        }
        userQueryWrapper.clear();
        userQueryWrapper.eq("mail", user.getMail());
        existsFlag = userMapper.exists(userQueryWrapper);
        if(existsFlag){
            throw new SQLMailConflictException();
        }
        // 如果不存在该用户，则顺利执行插入
        System.out.println(user);
        int res = userMapper.insert(user);
        return res == 1;
    }


    @Override
    public Boolean compareUserPassword(User user) throws SQLUserNotFoundException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", user.getUserName());
        userQueryWrapper.select("password");
        User resUser = userMapper.selectOne(userQueryWrapper);
        // 如果不存在该用户，则抛出异常
        if(resUser == null){
            throw new SQLUserNotFoundException();
        }
        return resUser.getPassword().equals(user.getPassword());
    }

    @Override
    public Boolean removeUser(String userName) throws SQLUserNotFoundException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", userName);
        User resUser = userMapper.selectOne(userQueryWrapper);
        // 如果不存在该用户，则抛出异常
        if(resUser == null){
            throw new SQLUserNotFoundException();
        }
        return userMapper.deleteById(resUser) == 1;
    }

    @Override
    public Long getUserIdByUsername(String userName){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", userName);
        userQueryWrapper.select("id");
        User resUser = userMapper.selectOne(userQueryWrapper);
        return resUser.getId();
    }

    @Override
    public User getUserByUsername(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", userName);
        return userMapper.selectOne(userQueryWrapper);
    }

    @Override
    public UserType getUserTypeByUsername(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", userName);
        userQueryWrapper.select("usertype");
        User resUser = userMapper.selectOne(userQueryWrapper);
        return resUser.getUserType();
    }

    @Override
    public Long getUserAccountByUsername(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", userName);
        userQueryWrapper.select("account");
        User resUser = userMapper.selectOne(userQueryWrapper);
        return resUser.getAccount();
    }

    @Override
    public Boolean updateUser(User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", user.getUserName());
        return userMapper.update(user, userQueryWrapper) == 1;
    }

    @Override
    public String login(User user) throws SQLUserNotFoundException {
        if ((boolean) compareUserPassword(user)) {
            return getUserTypeByUsername(user.getUserName()).name();
        } else {
            return null;
        }
    }
}
