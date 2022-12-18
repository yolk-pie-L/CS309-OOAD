package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.Assignment;
import com.example.live_video.exception.SQLAssignNameConflictException;
import com.example.live_video.mapper.AssignmentMapper;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AssignmentServiceImpl extends ServiceImpl<AssignmentMapper, Assignment> implements AssignmentService {

    @Autowired
    AssignmentMapper assignmentMapper;

    @Autowired
    CourseMapper courseMapper;

    @Override
    public Boolean createAssignment(Assignment assignment) throws SQLAssignNameConflictException {
        QueryWrapper<Assignment> assignQueryWrapper = new QueryWrapper<>();
        assignQueryWrapper.eq("course_id", assignment.getCourseId());
        assignQueryWrapper.eq("assignment_name", assignment.getAssignmentName());
        boolean existFlag = assignmentMapper.exists(assignQueryWrapper);
        if(existFlag){
            throw new SQLAssignNameConflictException();
        }
        int flag = assignmentMapper.insert(assignment);
        if(assignment.getAssignUrls() == null){
            return flag == 1;
        }
        for(String assign_url: assignment.getAssignUrls()){
            assignmentMapper.insertAssignUrlList(assignment.getId(), assign_url);
        }
        return flag == 1;
    }

    @Override
    @CacheEvict(value = "ass" ,key = "'Assign' +#p0")
    public Boolean removeAssignment(Long id) {
        return assignmentMapper.deleteById(id) == 1;
    }

    @Override
    @Cacheable(value = "ass" ,key = "'Assign' +#p0")
    public Assignment getOneAssignment(Long assignId) {
        Assignment assignment = assignmentMapper.selectById(assignId);
        if(assignment == null){
            return null;
        }
        assignment.setAssignUrls(assignmentMapper.getAssignUrlList(assignId));
        return assignment;
    }

    @Override
    public Long getAssignmentId(String courseName, String teacherName, String assignName) {
        Long courseId = courseMapper.getCourseId(teacherName, courseName);
        QueryWrapper<Assignment> assignQueryWrapper = new QueryWrapper<>();
        assignQueryWrapper.eq("course_id", courseId);
        assignQueryWrapper.eq("assignment_name", assignName);
        assignQueryWrapper.select("id");
        Assignment assignment = assignmentMapper.selectOne(assignQueryWrapper);
        return assignment.getId();
    }
}
