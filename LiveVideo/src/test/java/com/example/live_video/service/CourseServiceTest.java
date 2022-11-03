package com.example.live_video.service;

import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.MyException;
import com.example.live_video.exception.SQLCoursenameConflictException;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class CourseServiceTest {

    @Autowired
    CourseService courseService;

    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired(required = false)
    CourseMapper courseMapper;

    @Test
    void createCourse() {
        Course course = new Course("course", "teacher1", "test", 0L, null, null, null);
        User teacher = new User("teacher1", UserType.Teacher, "teacher1@mail", "123456");
        userMapper.insert(teacher);
        boolean flag = false;
        try {
            flag = courseService.createCourse(course);
        }catch (MyException e){
            flag = e instanceof SQLCoursenameConflictException;
        }
        assert flag;
        try {
            flag = courseService.createCourse(course);
        }catch (MyException e){
            flag = e instanceof SQLCoursenameConflictException;
        }
        assert flag;
        courseMapper.deleteById(course);
        userMapper.deleteById(teacher);
    }

    @Test
    void updateCourse() {
    }

    @Test
    void getReviewingCourses() {
    }
}
