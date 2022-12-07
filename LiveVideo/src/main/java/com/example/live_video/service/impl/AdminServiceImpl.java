package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.AdminRight;
import com.example.live_video.entity.User;
import com.example.live_video.exception.PermissionDeniedException;
import com.example.live_video.mapper.UserMapper;
import com.example.live_video.service.AdminService;
import com.example.live_video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<UserMapper, User> implements AdminService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Override
    public boolean addOrRemoveAdmin(String thisAdminName, String newAdminName) throws PermissionDeniedException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", thisAdminName);
        User thisAdmin = userMapper.selectOne(userQueryWrapper);
        if(thisAdmin.getAdminRight().equals(AdminRight.NonAdmin)){
            throw new PermissionDeniedException();
        }

        userQueryWrapper.clear();
        userQueryWrapper.eq("username", newAdminName);
        User newAdmin = userMapper.selectOne(userQueryWrapper);
        if(newAdmin.getAdminRight().equals(AdminRight.SuperAdmin)){ // 超级管理员
            throw new PermissionDeniedException();
        }
        if(newAdmin.getAdminRight().equals(AdminRight.Admin)){ // 普通管理员变成非管理员
            newAdmin.setAdminRight(AdminRight.NonAdmin);
        }else{
            newAdmin.setAdminRight(AdminRight.Admin); // 非管理员变成管理员
        }
        return userService.updateUser(newAdmin);
    }


    @Override
    public List<User> getUserList(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("username", userName);
        return userMapper.selectList(userQueryWrapper);
    }

    @Override
    public List<User> getUserList() {
        return this.list();
    }
}
