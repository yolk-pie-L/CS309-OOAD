package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.Assignment;
import com.example.live_video.exception.SQLAssignNameConflictException;

public interface AssignmentService extends IService<Assignment> {

    public Boolean createAssignment(Assignment assignment) throws SQLAssignNameConflictException;

    public Boolean removeAssignment(Assignment assignment);

    public Assignment getAssignmentByCourseNameTeacherNameAssignName(String courseName, String teacherName, String assignName);

    public Long getAssignmentIdByCourseNameTeacherNameAssignName(String courseName, String teacherName, String assignName);
}
