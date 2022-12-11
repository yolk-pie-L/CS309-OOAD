package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.Assignment;
import com.example.live_video.exception.SQLAssignNameConflictException;

public interface AssignmentService extends IService<Assignment> {

    public Boolean createAssignment(Assignment assignment) throws SQLAssignNameConflictException;

    public Boolean removeAssignment(Long id);

    public Assignment getOneAssignment(Long assignId);

    public Long getAssignmentId(String courseName, String teacherName, String assignName);
}
