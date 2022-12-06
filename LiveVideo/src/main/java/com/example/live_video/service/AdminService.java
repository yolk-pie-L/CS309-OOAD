package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.User;
import com.example.live_video.exception.PermissionDeniedException;

import java.util.List;

public interface AdminService extends IService<User> {

    public boolean addOrRemoveAdmin(String thisAdminName, String newAdminName) throws PermissionDeniedException;

    public List<User> getUserList(String userName);

    public List<User> getUserList();
}
