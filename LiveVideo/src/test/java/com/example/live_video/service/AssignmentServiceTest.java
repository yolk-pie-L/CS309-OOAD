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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        for (Course course : courses) {
            courseMapper.deleteById(course);
        }
        for (User user : users) {
            userMapper.deleteById(user);
        }
    }

    @Test
    void createAssignment() throws SQLAssignNameConflictException {
        List<String> urls = new ArrayList<>();
        urls.add("url1");
        urls.add("url2");
        Assignment assignment = new Assignment("ass1", courses.get(0).getId(), null, 100, urls, true, null);
        boolean flag1 = assignmentService.createAssignment(assignment);
        boolean flag2 = false;
        try {
            assignmentService.createAssignment(assignment);
        } catch (SQLAssignNameConflictException e) {
            flag2 = true;
        }
        assert flag1;
        assert flag2;
        assignmentMapper.deleteById(assignment);
    }

    @Test
    void removeAssignment() throws SQLAssignNameConflictException {
        List<String> urls = new ArrayList<>();
        urls.add("url1");
        urls.add("url2");
        Assignment assignment = new Assignment("ass1", courses.get(0).getId(), null, 100, urls, true, null);
        assignmentService.createAssignment(assignment);
        long count = assignmentService.count();
        assignmentService.removeAssignment(assignment.getId());
        long count2 = assignmentService.count();
        assertEquals(count2, count - 1);
    }

    @Test
    void getAssignmentByCourseNameTeacherNameAssignName() throws SQLAssignNameConflictException {
        List<String> urls = new ArrayList<>();
        urls.add("url1");
        urls.add("url2");
        Assignment assignment = new Assignment("ass1", courses.get(0).getId(), null, 100, urls, true, null);
        assignmentService.createAssignment(assignment);
        Assignment assignment1 = assignmentService.getOneAssignment(assignment.getId());
        System.out.println(assignment1);
        assertEquals(assignment.getId(), assignment1.getId());
        assertEquals(assignment.getAssignUrls(), assignment1.getAssignUrls());
    }

    @Test
    void testAssignmentCache() throws SQLAssignNameConflictException {
        Assignment assignment = new Assignment("assgin23123", courses.get(0).getId(), null, 100, null, true, null);
        assignmentService.createAssignment(assignment);
        System.out.println();
        System.out.println();
        System.out.println("--------------Start Testing Cache----------------");
        System.out.println("-------------Get One Assignment! Print Once-------");
        assignmentService.getOneAssignment(assignment.getId());
        System.out.println();
        System.out.println();
        assignmentService.getOneAssignment(assignment.getId());
        System.out.println();
        System.out.println();
        assignmentService.getOneAssignment(assignment.getId());
        System.out.println();
        System.out.println("===================================================");
        System.out.println("-------------Remove Assignment! Print Once-------");
        assignmentService.removeAssignment(assignment.getId());
        System.out.println();
        assertNull(assignmentService.getOneAssignment(assignment.getId()));
        System.out.println();
        System.out.println();
        assignmentService.getOneAssignment(assignment.getId());
        System.out.println();
        System.out.println();
        assignmentService.getOneAssignment(assignment.getId());
        System.out.println("=================================================");
    }
}