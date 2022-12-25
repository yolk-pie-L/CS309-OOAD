package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.Assignment;
import com.example.live_video.exception.SQLAssignNameConflictException;

import java.util.List;

public interface AssignmentService extends IService<Assignment> {

    public Boolean createAssignment(Assignment assignment) throws SQLAssignNameConflictException;

    public Boolean removeAssignment(Long id);

    public Assignment getOneAssignment(Long assignId);

    public Long getAssignmentId(long courseId, String assignName);

    public List<Assignment> getAssignmentsOfCourse(long courseId);
}
