package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.exception.MyException;
import com.example.live_video.exception.SQLCoursenameConflictException;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.UserMapper;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired(required = false)
    CourseMapper courseMapper;

    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired
    UserService userService;


    @Override
    public boolean createCourse(Course course) throws MyException {
        Boolean existsFlag = courseMapper.existCourse(course.getTeacherName(), course.getCourseName());
        if(existsFlag){
            throw new SQLCoursenameConflictException();
        }
        // 查询teacher的id
        Long teacherId = userService.getUserIdByUsername(course.getTeacherName());
        course.setTeacherId(teacherId);
        course.setStatus(CourseStatus.REVIEWING);
        return courseMapper.insert(course) == 1;
    }

    @Override
    public boolean updateCourse(Course course) {
        Long teacherId = userService.getUserIdByUsername(course.getTeacherName());
        course.setTeacherId(teacherId);
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("coursename", course.getCourseName());
        queryWrapper.eq("user_id", teacherId);
        return super.update(course, queryWrapper);
    }

    @Override
    public List<Course> getReviewingCourses(int recordsPerPage, int pageNum) {
        int limit = recordsPerPage;
        int offset = recordsPerPage * (pageNum - 1);
        return courseMapper.getReviewingCourses(limit, offset);
    }

    @Override
    public List<Course> getApprovedCourses(int recordsPerPage, int pageNum) {
        int limit = recordsPerPage;
        int offset = recordsPerPage * (pageNum - 1);
        return courseMapper.getApprovedCourses(limit, offset);
    }

    @Override
    public List<Course> getApprovedCoursesOfTeacher(int recordsPerPage, int pageNum, String teacherName) {
        int limit = recordsPerPage;
        int offset = recordsPerPage * (pageNum - 1);
        return courseMapper.getApprovedCoursesOfTeacher(limit, offset, teacherName);
    }

    @Override
    public List<Course> getFailedCoursesOfTeacher(int recordsPerPage, int pageNum, String teacherName) {
        int limit = recordsPerPage;
        int offset = recordsPerPage * (pageNum - 1);
        return courseMapper.getFailedCoursesOfTeacher(limit, offset, teacherName);
    }

    @Override
    public List<Course> getReviewingCoursesOfTeacher(int recordsPerPage, int pageNum, String teacherName) {
        int limit = recordsPerPage;
        int offset = recordsPerPage * (pageNum - 1);
        return courseMapper.getReviewingCoursesOfTeacher(limit, offset, teacherName);
    }
}