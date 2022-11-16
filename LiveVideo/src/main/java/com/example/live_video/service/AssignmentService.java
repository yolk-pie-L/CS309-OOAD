package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.Assignment;

public interface AssignmentService extends IService<Assignment> {

    public Object createAssignment(Assignment assignment);

    public Object removeAssignment(Assignment assignment);

    public Object getAssignmentByCourseNameTeacherNameAssignName(String courseName, String teacherName, String assignName);

}
