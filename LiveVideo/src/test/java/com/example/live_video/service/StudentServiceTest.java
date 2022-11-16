package com.example.live_video.service;

import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.StudentMapper;
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

import static org.junit.jupiter.api.Assertions.*;

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
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @BeforeEach
    void setUp() {
        User teacher1 = new User("t1", UserType.Teacher, "t1mail", "123456");
        User teacher2 = new User("t2", UserType.Teacher, "t2mail", "123");
        teachers.add(teacher1);
        teachers.add(teacher2);
        teachers.forEach(userMapper::insert);
        User student1 = new User("s1", UserType.Student, "s1mail", "123456");
        User student2 = new User("s2", UserType.Student, "s2mail", "123456");
        students.add(student1);
        students.add(student2);
        students.forEach(userMapper::insert);
        Course course1 = new Course("c1", teacher1.getUserName(), "t", 0L, "d", CourseStatus.APPROVED, null);
        Course course2 = new Course("c2",teacher1.getUserName(), "t", 0L, "d", CourseStatus.APPROVED, null);
        Course course3 = new Course("c3",teacher2.getUserName(), "t", 0L, "d", CourseStatus.APPROVED, null);
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.forEach(courseService::createCourse);
    }

    @AfterEach
    void tearDown() {
        for(Course c: courses){
            courseService.removeCourse(c.getTeacherName(), c.getCourseName());
        }
        students.forEach(userMapper::deleteById);
        teachers.forEach(userMapper::deleteById);
    }

    @Test
    void enrollCourse() {
        User s1 = students.get(0);
        User s2 = students.get(1);
        Course c1 = courses.get(0);
        Course c2 = courses.get(1);
        Course c3 = courses.get(2);
        studentService.enrollCourse(c1.getTeacherName(), c1.getCourseName(), s1.getUserName());
        studentService.enrollCourse( c2.getTeacherName(), c2.getCourseName(),s1.getUserName());
        studentService.enrollCourse(c3.getTeacherName(), c3.getCourseName(), s1.getUserName());
        studentService.enrollCourse( c1.getTeacherName(), c1.getCourseName(),s2.getUserName());
        studentService.enrollCourse(c3.getTeacherName(), c3.getCourseName(), s2.getUserName());
        List<Course> courses1 = studentService.getEnrolledCourse(s1.getUserName());
        assert courses1.size() == 3;
        List<Course> courses2 = studentService.getEnrolledCourse(s2.getUserName());
        assert courses2.size() == 2;
        courses2.forEach(System.out::println);
        for(Course course: courses2){
            assert course.getCourseName().equals(c1.getCourseName()) || course.getCourseName().equals(c3.getCourseName());
        }
        assert !courses2.get(0).getCourseName().equals(courses2.get(1).getCourseName());
        studentService.exitCourse(c1.getTeacherName(), c1.getCourseName(), s1.getUserName());
        studentService.exitCourse(c2.getTeacherName(), c2.getCourseName(), s1.getUserName());
        studentService.exitCourse(c3.getTeacherName(), c3.getCourseName(), s1.getUserName());
        studentService.exitCourse(c1.getTeacherName(), c1.getCourseName(), s2.getUserName());
        studentService.exitCourse(c3.getTeacherName(), c3.getCourseName(), s2.getUserName());
        courses1 = studentService.getEnrolledCourse(s1.getUserName());
        assert courses1.size() == 0;
        courses2 = studentService.getEnrolledCourse(s2.getUserName());
        assert courses2.size() == 0;
    }

    @Test
    void exitCourse() {
    }

    @Test
    void getEnrolledCourse() {
    }
}