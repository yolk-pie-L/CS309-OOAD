package com.example.live_video.mapper;

import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@Transactional
@Rollback
public class CourseMapperTest {

    @Autowired(required = false)
    CourseMapper courseMapper;

    @Autowired(required = false)
    UserMapper userMapper;

    List<User> allUsers = new ArrayList<>();

    List<Course> allCourses = new ArrayList<>();

    public void setUp(){
        User user = new User("shenyun", UserType.Teacher, "sy@mail.sustech.edu.cn", "123456");
        userMapper.insert(user);
        Course course = new Course("DSAA", user.getId(), "CS", 0L, null, CourseStatus.REVIEWING, null);
        course.setTeacherName(user.getUserName());
        courseMapper.insert(course);
        allCourses.add(course);
        allUsers.add(user);
    }

    public void tearDown(){
        courseMapper.deleteBatchIds(allCourses);
        userMapper.deleteBatchIds(allUsers);
    }

    @Test
    public void testExistCourse(){
        User user = new User("shenyun", UserType.Teacher, "sy@mail.sustech.edu.cn", "123456");
        userMapper.insert(user);
        Course course = new Course("DSAA", user.getId(), "CS", 0L, null, CourseStatus.REVIEWING, null);
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

    @Test
    public void getCourseIdByCourseNameTeacherName(){
        setUp();
        Course course = allCourses.get(0);
        Long id = courseMapper.getCourseId(course.getTeacherName(), course.getCourseName());
        assert Objects.equals(id, course.getId());
        tearDown();
    }

    @Test
    public void testSelectAll(){
        User user = new User("shenyun", UserType.Teacher, "sy@mail.sustech.edu.cn", "123456");
        userMapper.insert(user);
        Course course = new Course("DSAA", user.getId(), "CS", 2L, null, CourseStatus.REVIEWING, "courseurl");
        courseMapper.insert(course);
        List<Course> courseList = courseMapper.selectList(null);
        courseList.forEach(System.out::println);
        System.out.println(courseList.size());
        courseMapper.deleteById(course);
        userMapper.deleteById(user);
    }
}
