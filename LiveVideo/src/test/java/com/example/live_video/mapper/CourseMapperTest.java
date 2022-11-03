package com.example.live_video.mapper;

import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseMapperTest {

    @Autowired(required = false)
    CourseMapper courseMapper;

    @Autowired(required = false)
    UserMapper userMapper;

    @Test
    public void testExistCourse(){
        User user = new User("shenyun", UserType.Teacher, "sy@mail.sustech.edu.cn", "123456");
        userMapper.insert(user);
        Course course = new Course("DSAA", user.getUserName(), "CS", 0L, null, CourseStatus.REVIEWING, null);
        courseMapper.insert(course);
        Boolean flag = courseMapper.existCourse(user.getUserName(), course.getCourseName());
        assert flag;
        flag = courseMapper.existCourse(user.getUserName(), course.getCourseName() + "1");
        assert !flag;
        userMapper.deleteById(user);
        flag = courseMapper.existCourse(user.getUserName(), course.getCourseName() + "1");
        assert !flag;
        courseMapper.deleteById(course);
        flag = courseMapper.existCourse(user.getUserName(), course.getCourseName() + "1");
        assert !flag;
    }
}
