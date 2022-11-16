package com.example.live_video.service;

import com.example.live_video.entity.*;
import com.example.live_video.exception.SQLAssignNameConflictException;
import com.example.live_video.mapper.AssignmentMapper;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class AssignmentServiceTest {

    List<User> users = new ArrayList<>();

    List<Course> courses = new ArrayList<>();

    @Autowired
    UserMapper userMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    AssignmentMapper assignmentMapper;
    @Autowired
    AssignmentService assignmentService;

    @BeforeEach
    void setUp() {
        User user = new User("user1", UserType.Teacher, "user1@mail", "123456");
        users.add(user);
        userMapper.insert(user);
        Course course = new Course("course1", user.getId(), "cs", 0L, null, CourseStatus.APPROVED, null);
        courses.add(course);
        courseMapper.insert(course);
    }

    @AfterEach
    void tearDown() {
        for(Course course: courses){
            courseMapper.deleteById(course);
        }
        for(User user: users){
            userMapper.deleteById(user);
        }
    }

    @Test
    void createAssignment() {
        List<String> urls = new ArrayList<>();
        urls.add("url1");
        urls.add("url2");
        Assignment assignment = new Assignment("ass1", courses.get(0).getCourseName(), users.get(0).getUserName(), null, 100, true, null, urls);
        boolean flag1 = (boolean) assignmentService.createAssignment(assignment);
        boolean flag2 = assignmentService.createAssignment(assignment) instanceof SQLAssignNameConflictException;
        assert flag1;
        assert flag2;
        assignmentMapper.deleteById(assignment);
    }

    @Test
    void removeAssignment() {
        List<String> urls = new ArrayList<>();
        urls.add("url1");
        urls.add("url2");
        Assignment assignment = new Assignment("ass1", courses.get(0).getCourseName(), users.get(0).getUserName(), null, 100, true, null, urls);
        assignmentService.createAssignment(assignment);
        assignmentService.removeAssignment(assignment);
    }

    @Test
    void getAssignmentByCourseNameTeacherNameAssignName() {
        List<String> urls = new ArrayList<>();
        urls.add("url1");
        urls.add("url2");
        Assignment assignment = new Assignment("ass1", courses.get(0).getCourseName(), users.get(0).getUserName(), null, 100, true, null, urls);
        assignmentService.createAssignment(assignment);
        Object assignment1 = assignmentService.getAssignmentByCourseNameTeacherNameAssignName(courses.get(0).getCourseName(), users.get(0).getUserName(), assignment.getAssignmentName());
        assert assignment1 instanceof Assignment;
        System.out.println(assignment1);
    }
}