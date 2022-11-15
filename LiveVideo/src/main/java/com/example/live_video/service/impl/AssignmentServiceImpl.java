package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.Assignment;
import com.example.live_video.exception.SQLAssignNameConflictException;
import com.example.live_video.mapper.AssignmentMapper;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentServiceImpl extends ServiceImpl<AssignmentMapper, Assignment> implements AssignmentService {

    @Autowired
    AssignmentMapper assignmentMapper;

    @Autowired
    CourseMapper courseMapper;

    @Override
    public Object createAssignment(Assignment assignment) {
        Long courseId = courseMapper.getCourseIdByTeacherNameCourseName(assignment.getTeacherName(), assignment.getCourseName());
        assignment.setCourseId(courseId);
        QueryWrapper<Assignment> assignQueryWrapper = new QueryWrapper<>();
        assignQueryWrapper.eq("course_id", courseId);
        assignQueryWrapper.eq("assignment_name", assignment.getAssignmentName());
        boolean existFlag = assignmentMapper.exists(assignQueryWrapper);
        if(existFlag){
            return new SQLAssignNameConflictException();
        }
        int flag = assignmentMapper.insert(assignment);
        if(assignment.getAssignUrls() == null){
            return flag;
        }
        for(String assign_url: assignment.getAssignUrls()){
            assignmentMapper.insertAssignUrls(assignment.getId(), assign_url);
        }
        return flag == 1;
    }

    @Override
    public Object removeAssignment(Assignment assignment) {
        Long courseId = courseMapper.getCourseIdByTeacherNameCourseName(assignment.getTeacherName(), assignment.getCourseName());
        return assignmentMapper.deleteAssignment(courseId, assignment.getAssignmentName());
    }

    @Override
    public Object getAssignmentByCourseNameTeacherNameAssignName(String courseName, String teacherName, String assignName) {
        Long courseId = courseMapper.getCourseIdByTeacherNameCourseName(teacherName, courseName);
        QueryWrapper<Assignment> assignQueryWrapper = new QueryWrapper<>();
        assignQueryWrapper.eq("course_id", courseId);
        assignQueryWrapper.eq("assignment_name", assignName);
        Assignment assignment = assignmentMapper.selectOne(assignQueryWrapper);
        assignment.setAssignUrls(assignmentMapper.getAssignUrls(courseId, assignName));
        return assignment;
    }
}
