package com.example.live_video.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.live_video.entity.*;
import com.example.live_video.exception.EnrollCourseException;
import com.example.live_video.exception.SQLAssignNameConflictException;
import com.example.live_video.exception.SQLCoursenameConflictException;
import com.example.live_video.mapper.*;
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

@SpringBootTest
@Transactional
@Rollback
class StudentServiceTest {

    List<User> teachers = new ArrayList<>();

    List<User> students = new ArrayList<>();

    List<Course> courses = new ArrayList<>();

    @Autowired
    UserMapper userMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    SectionMapper sectionMapper;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @Autowired
    AssignmentMapper assignmentMapper;

    @Autowired
    AssignmentService assignmentService;

    List<Section> sections = new ArrayList<>();

    @BeforeEach
    void setUp() throws SQLCoursenameConflictException {
        User teacher1 = new User("t1", UserType.Teacher, "t1mail", "123456");
        User teacher2 = new User("t2", UserType.Teacher, "t2mail", "123");
        teachers.add(teacher1);
        teachers.add(teacher2);
        teachers.forEach(userMapper::insert);
        User student1 = new User("s1", UserType.Student, "s1mail", "123456", null, 100L);
        User student2 = new User("s2", UserType.Student, "s2mail", "123456", null, 10L);
        User student3 = new User("s3", UserType.Student, "s3m", "2ed", null, 2000L);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.forEach(userMapper::insert);
        Course course1 = new Course("c1", teacher1.getUserName(), "t", 10L, "d", CourseStatus.APPROVED, null);
        Course course2 = new Course("c2", teacher1.getUserName(), "t", 10L, "d", CourseStatus.APPROVED, null);
        Course course3 = new Course("c3", teacher2.getUserName(), "t", 10L, "d", CourseStatus.APPROVED, null);
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        for (Course c : courses) {
            courseService.createCourse(c);
        }
        Section section1 = new Section("s1", course1.getId(), "s1", 99);
        Section section2 = new Section("s2", course1.getId(), "s2", 88);
        Section section3 = new Section("s3", course2.getId(), "s3", 77);
        sectionMapper.insert(section1);
        sectionMapper.insert(section2);
        sectionMapper.insert(section3);
        sections.add(section1);
        sections.add(section2);
        sections.add(section3);
    }

    @AfterEach
    void tearDown() {
        for (Course c : courses) {
            courseService.removeCourse(c.getId());
        }
        students.forEach(userMapper::deleteById);
        teachers.forEach(userMapper::deleteById);
    }

    @Test
    void enrollCourse() throws EnrollCourseException {
        User s1 = students.get(0);
        User s2 = students.get(1);
        Course c1 = courses.get(0);
        Course c2 = courses.get(1);
        Course c3 = courses.get(2);
        studentService.enrollCourse(c1.getId(), s1.getUserName());
        studentService.enrollCourse(c2.getId(), s1.getUserName());
        studentService.enrollCourse(c3.getId(), s1.getUserName());
        studentService.enrollCourse(c1.getId(), s2.getUserName());
        boolean res = false;
        try {
            studentService.enrollCourse(c3.getId(), s2.getUserName());
        } catch (EnrollCourseException e) {
            res = true;
        }
        assert res;
        List<Course> courses1 = studentService.getEnrolledCourseList(s1.getUserName());
        assert courses1.size() == 3;
        List<Course> courses2 = studentService.getEnrolledCourseList(s2.getUserName());
        assert courses2.size() == 1;
        courses2.forEach(System.out::println);
        assert courses2.get(0).getCourseName().equals(c1.getCourseName());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", s1.getUserName());
        User u1 = userMapper.selectOne(userQueryWrapper);
        assert u1.getAccount().equals(70L);
        System.out.println(u1);
        studentService.exitCourse(c1.getId(), s1.getUserName());
        studentService.exitCourse(c2.getId(), s1.getUserName());
        studentService.exitCourse(c3.getId(), s1.getUserName());
        studentService.exitCourse(c1.getId(), s2.getUserName());
        studentService.exitCourse(c3.getId(), s2.getUserName());
        courses1 = studentService.getEnrolledCourseList(s1.getUserName());
        assert courses1.size() == 0;
        courses2 = studentService.getEnrolledCourseList(s2.getUserName());
        assert courses2.size() == 0;
    }

    @Test
    void exitCourse() {
        // enroll course
    }

    @Test
    void getEnrolledCourse() {
        // enroll course
    }

    @Test
    void setStudentAssignGrade() throws SQLAssignNameConflictException {
        User s1 = students.get(0);
        User s2 = students.get(1);
        Course c1 = courses.get(0);
        Course c2 = courses.get(1);
        Course c3 = courses.get(2);
        Assignment assignment1 = new Assignment("assi1", c1.getId(), null, 100, null,true, null);
        Assignment assignment2 = new Assignment("assi2", c1.getId(), null, 120, null, false, null);
        assignmentService.createAssignment(assignment1);
        assignmentService.createAssignment(assignment2);
        List<String> urls1 = new ArrayList<>();
        urls1.add("l1");
        urls1.add("l2");
        studentService.submitAssignment(s1.getUserName(), assignment1.getId(), urls1);
        studentService.setStudentAssignGrade(s1.getUserName(), assignment1.getId(), 100);
        int grade = studentService.getStudentAssignGrade(s1.getUserName(), assignment1.getId());
        assert grade==100;
        List<String> res = studentService.getStudentAssignmentUrlList(s1.getUserName(),  assignment1.getId());
        System.out.println(res);
        assert res.contains("l1");
        assert res.contains("l2");
        urls1.add("l3");
        studentService.resubmitAssignment(s1.getUserName(), assignment1.getId(), urls1);
        res = studentService.getStudentAssignmentUrlList(s1.getUserName(), assignment1.getId());
        assert res.size() == 3;
        studentService.setStudentAssignGrade(s1.getUserName(), assignment1.getId(), 100);
    }

    @Test
    void submitAssignment() {
        //in setStudentAssignGrade
    }

    @Test
    void getStudentAssignmentUrls() {
        //in setStudentAssignGrade
    }

    @Test
    void getStudentAssignGrade() {
    }

    @Test
    void setStudentSectionProgress() throws EnrollCourseException {
        User s1 = students.get(0);
        User s2 = students.get(2);
        Course c1 = courses.get(0);
        Course c2 = courses.get(1);
        Course c3 = courses.get(2);
        studentService.enrollCourse(c1.getId(), s1.getUserName());
        studentService.enrollCourse(c2.getId(), s1.getUserName());
        studentService.enrollCourse(c3.getId(), s1.getUserName());
        studentService.enrollCourse(c1.getId(), s2.getUserName());
        studentService.setStudentSectionProgress(s1.getId(), sections.get(0).getId(), 0.5);
        studentService.setStudentSectionProgress(s2.getId(), sections.get(0).getId(), 0.3);
        Section section4 = new Section("s4", c1.getId(), "s3", 77);
        sectionMapper.insert(section4);
        sections.add(section4);
        studentService.setStudentSectionProgress(s1.getId(), section4.getId(), 0.2);
    }

    @Test
    void getStudentListOfOneCourse() throws EnrollCourseException {
        User s1 = students.get(0);
        User s3 = students.get(2);
        Course c1 = courses.get(0);
        Course c2 = courses.get(1);
        Course c3 = courses.get(2);
        studentService.enrollCourse(c1.getId(), s1.getUserName());
        studentService.enrollCourse(c2.getId(), s1.getUserName());
        studentService.enrollCourse(c3.getId(), s3.getUserName());
        studentService.enrollCourse(c1.getId(), s3.getUserName());
        List<User> userList = studentService.getStudentListOfOneCourse(c1.getId());
        List<User> userList1 = studentService.getStudentListOfOneCourse(c2.getId());
        List<User> userList2 = studentService.getStudentListOfOneCourse(c3.getId());
        assertEquals(2, userList.size());
        assertEquals(1, userList1.size());
        assertEquals(1, userList2.size());
        assertEquals(s1.getId(), userList1.get(0).getId());
        assertEquals(s3.getId(), userList2.get(0).getId());
    }
}