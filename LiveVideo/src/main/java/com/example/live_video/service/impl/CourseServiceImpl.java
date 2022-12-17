package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
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
    public Boolean createCourse(Course course) throws SQLCoursenameConflictException {
        Boolean existsFlag = courseMapper.existCourse(course.getTeacherName(), course.getCourseName());
        if(existsFlag){
            throw new SQLCoursenameConflictException();
        }
        // 查询teacher的id
        Long teacherId = userService.getUserId(course.getTeacherName());
        course.setTeacherId(teacherId);
        course.setStatus(CourseStatus.REVIEWING);
        return courseMapper.insert(course) == 1;
    }

    @Override
    public boolean updateCourse(Course course) {
        Long teacherId = userService.getUserId(course.getTeacherName());
        course.setTeacherId(teacherId);
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("course_name", course.getCourseName());
        courseQueryWrapper.eq("teacher_id", teacherId);
        return super.update(course, courseQueryWrapper);
    }

    @Override
    public boolean removeCourse(Long courseId) {
        return courseMapper.deleteById(courseId) == 1;
    }

    @Override
    public List<Course> getReviewingCourseList() {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("status", CourseStatus.REVIEWING);
        return courseMapper.selectList(courseQueryWrapper);
    }

    @Override
    public List<Course> getApprovedCourseList(int recordsPerPage, int pageNum) {
        int limit = recordsPerPage;
        int offset = recordsPerPage * (pageNum - 1);
        return courseMapper.getApprovedCourseList(limit, offset);
    }

    @Override
    public List<Course> getApprovedCourseListOfTeacher(int recordsPerPage, int pageNum, String teacherName) {
        int limit = recordsPerPage;
        int offset = recordsPerPage * (pageNum - 1);
        return courseMapper.getApprovedCourseListOfTeacher(limit, offset, teacherName);
    }

    @Override
    public List<Course> getFailedCourseListOfTeacher(int recordsPerPage, int pageNum, String teacherName) {
        int limit = recordsPerPage;
        int offset = recordsPerPage * (pageNum - 1);
        return courseMapper.getFailedCourseListOfTeacher(limit, offset, teacherName);
    }

    @Override
    public List<Course> getReviewingCourseListOfTeacher(int recordsPerPage, int pageNum, String teacherName) {
        int limit = recordsPerPage;
        int offset = recordsPerPage * (pageNum - 1);
        return courseMapper.getReviewingCourseListOfTeacher(limit, offset, teacherName);
    }

    @Override
    public Course getOneCourse(Long courseId) {
        return courseMapper.selectById(courseId);
    }

    @Override
    public String getCoursePrivateKeyUrl(String teacherName, String courseName) {
        Long courseId = courseMapper.getCourseId(teacherName, courseName);
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("id", courseId);
        courseQueryWrapper.select("private_key_url");
        return courseMapper.selectOne(courseQueryWrapper).getPrivateKeyUrl();
    }

    @Override
    public List<Course> getApprovedCourseList(int recordsPerPage, int pageNum, String courseName) {
        int limit = recordsPerPage;
        int offset = recordsPerPage * (pageNum - 1);
        return courseMapper.getApprovedCourseListByCourseName(limit, offset, courseName);
    }

    @Override
    public Course getOneCourse(String teacherName, String courseName) {
        return courseMapper.getOneCourse(teacherName, courseName);
    }

    @Override
    public Course getOneApprovedCourse(String teacherName, String courseName) {
        return courseMapper.getOneApprovedCourse(teacherName, courseName);
    }

    public List<Course> getAllCourse() {
        // TODO
        return null;
    }
}

