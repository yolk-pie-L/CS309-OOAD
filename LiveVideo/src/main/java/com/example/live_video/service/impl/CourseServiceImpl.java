package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.entity.User;
import com.example.live_video.exception.MyException;
import com.example.live_video.exception.SQLCoursenameConflictException;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.UserMapper;
import com.example.live_video.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired(required = false)
    CourseMapper courseMapper;

    @Autowired(required = false)
    UserMapper userMapper;


    @Override
    public boolean createCourse(Course course) throws MyException {
        Boolean existsFlag = courseMapper.existCourse(course.getTeacherName(), course.getCourseName());
        if(existsFlag){
            throw new SQLCoursenameConflictException();
        }
        // 查询teacher的id
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", course.getTeacherName());
        userQueryWrapper.select("id");
        User resUser = userMapper.selectOne(userQueryWrapper);

        course.setTeacherId(resUser.getId());
        course.setStatus(CourseStatus.REVIEWING);
        return courseMapper.insert(course) == 1;
    }

    @Override
    public boolean updateCourse(Course course, User teacher) {

        return false;
    }

    @Override
    public List<Course> getReviewingCourses() {
        return null;
    }
}