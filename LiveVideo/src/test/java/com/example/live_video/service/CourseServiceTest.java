package com.example.live_video.service;

import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.MyException;
import com.example.live_video.exception.SQLCoursenameConflictException;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.UserMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
public class CourseServiceTest {

    @Autowired
    CourseService courseService;

    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired(required = false)
    CourseMapper courseMapper;

    List<Course> allCourses = new ArrayList<>();

    List<User> allUsers = new ArrayList<>();

    // teacher1有三个review, 一个failed, 一个approved
    // teacher2有一个review
    void setUp() {
        User teacher1 = new User("teacher1", UserType.Teacher, "teacher1@mail", "123456");
        User teacher2 = new User("teacher2", UserType.Teacher, "teacher2@mail", "123456");
        userMapper.insert(teacher1);
        userMapper.insert(teacher2);
        Course rc1_t1 = new Course("rc1_t1", teacher1.getId(), "test", 0L, "review", CourseStatus.REVIEWING, "url");
        Course rc2_t1 = new Course("rc2_t1", teacher1.getId(), "test", 0L, "review", CourseStatus.REVIEWING, "url");
        Course rc3_t1 = new Course("rc3_t1", teacher1.getId(), "test", 0L, "review", CourseStatus.REVIEWING, "url");
        Course ac1_t1 = new Course("ac_t1", teacher1.getId(), "test", 0L, "approve", CourseStatus.APPROVED, "url");
        Course fc1_t1 = new Course("fc_t1", teacher1.getId(), "test", 0L, "fail", CourseStatus.FAILED, "url");
        Course rc1_t2 = new Course("rc1_t1", teacher2.getId(), "test", 0L, "the same name as rc1_t1", CourseStatus.REVIEWING, "url");
        courseMapper.insert(rc1_t1);
        courseMapper.insert(rc2_t1);
        courseMapper.insert(rc3_t1);
        courseMapper.insert(ac1_t1);
        courseMapper.insert(fc1_t1);
        courseMapper.insert(rc1_t2);
        allCourses.add(rc1_t1);
        allCourses.add(rc2_t1);
        allCourses.add(rc3_t1);
        allCourses.add(ac1_t1);
        allCourses.add(fc1_t1);
        allCourses.add(rc1_t2);
        allUsers.add(teacher1);
        allUsers.add(teacher2);
    }

    void tearDown(){
        courseMapper.deleteBatchIds(allCourses);
        userMapper.deleteBatchIds(allUsers);
    }



    @Test
    void createCourse() {
        Course course = new Course("course", "teacher1", "test", 0L, null, null, null);
        User teacher = new User("teacher1", UserType.Teacher, "teacher1@mail", "123456");
        userMapper.insert(teacher);
        boolean flag = false;
        try {
            flag = courseService.createCourse(course);
        } catch (MyException e) {
            flag = e instanceof SQLCoursenameConflictException;
        }
        assert flag;
        try {
            flag = courseService.createCourse(course);
        } catch (MyException e) {
            flag = e instanceof SQLCoursenameConflictException;
        }
        assert flag;
        courseMapper.deleteById(course);
        userMapper.deleteById(teacher);
    }

    @Test
    void updateCourse() {
        User teacher = new User("teacher1", UserType.Teacher, "teacher1@mail", "123456");
        userMapper.insert(teacher);
        Course course = new Course("course", teacher.getId(), "test", 0L, "HELLP", CourseStatus.APPROVED, "url");
        Course course_copy = new Course("course", teacher.getUserName(), null, 0L, null, CourseStatus.FAILED, "NEWURL");
        courseMapper.insert(course);
        courseService.updateCourse(course_copy);
        Course res = courseMapper.selectById(course.getId());
        assert res.getStatus() == course_copy.getStatus();
        assert res.getDescription().equals(course.getDescription());
        assert res.getPictureUrl().equals(course_copy.getPictureUrl());
        userMapper.deleteById(teacher);
        courseMapper.deleteById(course);
    }

    @Test
    void getReviewingCourses() {
        User teacher = new User("teacher1", UserType.Teacher, "teacher1@mail", "123456");
        userMapper.insert(teacher);
        Course rc1 = new Course("rc1", teacher.getId(), "test", 0L, "HELLP", CourseStatus.REVIEWING, "url");
        Course rc2 = new Course("rc2", teacher.getId(), "test", 0L, "HELLP", CourseStatus.REVIEWING, "url");
        Course rc3 = new Course("rc3", teacher.getId(), "test", 0L, "HELLP", CourseStatus.REVIEWING, "url");
        Course ac = new Course("ac", teacher.getId(), "test", 0L, "HELLP", CourseStatus.APPROVED, "url");
        courseMapper.insert(rc1);
        courseMapper.insert(rc2);
        courseMapper.insert(rc3);
        courseMapper.insert(ac);
        List<Course> courseList = courseService.getReviewingCourses(2, 1);
        assert courseList.size() == 2;
        for (Course course : courseList) {
            assert course.getStatus() == CourseStatus.REVIEWING;
        }
        courseList = courseService.getReviewingCourses(2, 2);
        assert courseList.size() == 1;
        assert courseList.get(0).getCourseName().equals("rc3");
        courseMapper.deleteById(rc1);
        courseMapper.deleteById(rc2);
        courseMapper.deleteById(rc3);
        courseMapper.deleteById(ac);
        userMapper.deleteById(teacher);
    }

    @Test
    void getApprovedCourses() {
        setUp();
        List<Course> courseList = courseService.getApprovedCourses(2, 2);
        assert courseList.size() == 0;
        courseList = courseService.getApprovedCourses(1, 1);
        assert courseList.size() == 1;
        tearDown();
    }

    @Test
    void getApprovedCoursesOfTeacher() {
        setUp();
        List<Course> courseList = courseService.getApprovedCoursesOfTeacher(1, 1, "teacher1");
        assert courseList.size() == 1;
        assert courseList.get(0).getStatus() == CourseStatus.APPROVED;
        courseList = courseService.getApprovedCoursesOfTeacher(1, 1, "teacher2");
        assert courseList.size() == 0;
        tearDown();
    }

    @Test
    void getFailedCoursesOfTeacher() {
        setUp();
        List<Course> courseList = courseService.getFailedCoursesOfTeacher(1, 1, "teacher1");
        assert courseList.size() == 1;
        assert courseList.get(0).getStatus().equals(allCourses.get(4).getStatus());
        assert courseList.get(0).getId().equals(allCourses.get(4).getId());
        courseList = courseService.getFailedCoursesOfTeacher(1, 1, "teacher2");
        assert courseList.size() == 0;
        tearDown();
    }

    @Test
    void getReviewingCoursesOfTeacher() {
        setUp();
        List<Course> courseList = courseService.getReviewingCoursesOfTeacher(4, 1, "teacher1");
        assert courseList.size() == 3;
        assert courseList.get(0).getStatus() == CourseStatus.REVIEWING;
        courseList = courseService.getReviewingCoursesOfTeacher(4, 1, "teacher2");
        assert courseList.size() == 1;
        assert Objects.equals(courseList.get(0).getId(), allCourses.get(allCourses.size() - 1).getId());
        tearDown();
    }
}