package com.example.live_video.service;

import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.SQLCoursenameConflictException;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.UserMapper;
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
        User teacher3 = new User("teacher3", UserType.Teacher, "t3@mail", "32");
        User teacher4 = new User("teacher4", UserType.Teacher, "t4", "3234");
        userMapper.insert(teacher1);
        userMapper.insert(teacher2);
        userMapper.insert(teacher3);
        userMapper.insert(teacher4);
        Course rc1_t1 = new Course("rc1_t1", teacher1.getId(), "test", 0L, "review", CourseStatus.REVIEWING, "assign_url");
        Course ac1_t3 = new Course("rc1_t1", teacher3.getId(), "test", 0L, "review", CourseStatus.APPROVED, "assign_url");
        Course ac1_t4 = new Course("rc1_t1", teacher4.getId(), "test", 0L, "review", CourseStatus.APPROVED, "assign_url");
        Course rc2_t1 = new Course("rc2_t1", teacher1.getId(), "test", 0L, "review", CourseStatus.REVIEWING, "assign_url");
        Course rc3_t1 = new Course("rc3_t1", teacher1.getId(), "test", 0L, "review", CourseStatus.REVIEWING, "assign_url");
        Course ac1_t1 = new Course("awsss", teacher1.getId(), "test", 0L, "approve", CourseStatus.APPROVED, "assign_url");
        Course fc1_t1 = new Course("fc_t1", teacher1.getId(), "test", 0L, "fail", CourseStatus.FAILED, "assign_url");
        Course rc1_t2 = new Course("rc1_t1", teacher2.getId(), "test", 0L, "the same name as rc1_t1", CourseStatus.REVIEWING, "assign_url");
        courseMapper.insert(rc1_t1);
        courseMapper.insert(rc2_t1);
        courseMapper.insert(rc3_t1);
        courseMapper.insert(ac1_t3);
        courseMapper.insert(ac1_t4);
        courseMapper.insert(ac1_t1);
        courseMapper.insert(fc1_t1);
        courseMapper.insert(rc1_t2);
        allCourses.add(rc1_t1);
        allCourses.add(rc2_t1);
        allCourses.add(rc3_t1);
        allCourses.add(ac1_t1);
        allCourses.add(fc1_t1);
        allCourses.add(rc1_t2);
        allCourses.add(ac1_t3);
        allCourses.add(ac1_t4);
        allUsers.add(teacher1);
        allUsers.add(teacher2);
        allUsers.add(teacher3);
        allUsers.add(teacher4);
    }

    void tearDown(){
        courseMapper.deleteBatchIds(allCourses);
        userMapper.deleteBatchIds(allUsers);
    }


    @Test
    void createCourse() throws SQLCoursenameConflictException {
        Course course = new Course("course", "teacher1", "test", 0L, null, null, null);
        User teacher = new User("teacher1", UserType.Teacher, "teacher1@mail", "123456");
        userMapper.insert(teacher);
        boolean flag = false;
        flag = courseService.createCourse(course);
        assert flag;
        flag = false;
        try {
            courseService.createCourse(course);
        }catch (SQLCoursenameConflictException e){
            flag = true;
        }
        assert flag;
        courseMapper.deleteById(course);
        userMapper.deleteById(teacher);
    }

    @Test
    void updateCourse() {
        User teacher = new User("teacher1", UserType.Teacher, "teacher1@mail", "123456");
        userMapper.insert(teacher);
        Course course = new Course("course", teacher.getId(), "test", 0L, "HELLP", CourseStatus.APPROVED, "assign_url");
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
        Course rc1 = new Course("rc1", teacher.getId(), "test", 0L, "HELLP", CourseStatus.REVIEWING, "assign_url");
        rc1.setTeacherName(teacher.getUserName());
        Course rc2 = new Course("rc2", teacher.getId(), "test", 0L, "HELLP", CourseStatus.REVIEWING, "assign_url");
        Course rc3 = new Course("rc3", teacher.getId(), "test", 0L, "HELLP", CourseStatus.REVIEWING, "assign_url");
        Course ac = new Course("ac", teacher.getId(), "test", 0L, "HELLP", CourseStatus.APPROVED, "assign_url");
        courseMapper.insert(rc1);
        courseMapper.insert(rc2);
        courseMapper.insert(rc3);
        courseMapper.insert(ac);
        List<Course> courseList = courseService.getReviewingCourseList();
        assert courseList.size() == 3;
        for (Course course : courseList) {
            assert course.getStatus() == CourseStatus.REVIEWING;
        }
        courseMapper.deleteById(rc1);
        courseMapper.deleteById(rc2);
        courseMapper.deleteById(rc3);
        courseMapper.deleteById(ac);
        userMapper.deleteById(teacher);
    }

    @Test
    void getApprovedCourses() {
        setUp();
        List<Course> courseList = courseService.getApprovedCourseList(2, 1);
        assert courseList.size() == 2;
        courseList = courseService.getApprovedCourseList(1, 1);
        System.out.println(courseList);
        assert courseList.size() == 1;
        tearDown();
    }

    @Test
    void getApprovedCoursesOfTeacher() {
        setUp();
        List<Course> courseList = courseService.getApprovedCourseListOfTeacher(1, 1, "teacher1");
        assert courseList.size() == 1;
        assert courseList.get(0).getStatus() == CourseStatus.APPROVED;
        courseList = courseService.getApprovedCourseListOfTeacher(1, 1, "teacher2");
        assert courseList.size() == 0;
        tearDown();
    }

    @Test
    void getFailedCoursesOfTeacher() {
        setUp();
        List<Course> courseList = courseService.getFailedCourseListOfTeacher(1, 1, "teacher1");
        assert courseList.size() == 1;
        assert courseList.get(0).getStatus().equals(allCourses.get(4).getStatus());
        assert courseList.get(0).getId().equals(allCourses.get(4).getId());
        courseList = courseService.getFailedCourseListOfTeacher(1, 1, "teacher2");
        assert courseList.size() == 0;
        tearDown();
    }

    @Test
    void getReviewingCoursesOfTeacher() {
        setUp();
        List<Course> courseList = courseService.getReviewingCourseListOfTeacher(4, 1, "teacher1");
        assert courseList.size() == 3;
        assert courseList.get(0).getStatus() == CourseStatus.REVIEWING;
        courseList = courseService.getReviewingCourseListOfTeacher(4, 1, "teacher2");
        assert courseList.size() == 1;
        assert Objects.equals(courseList.get(0).getId(), allCourses.get(5).getId());
        tearDown();
    }

    @Test
    void removeCourse(){
        User teacher = new User("t", UserType.Teacher, "t@m", "123");
        userMapper.insert(teacher);
        Course course = new Course("aa", teacher.getId(), "cs", 1L, "aab", CourseStatus.APPROVED, "assign_url");
        courseMapper.insert(course);
        Long count1 = courseMapper.selectCount(null);
        boolean flag = courseService.removeCourse(course.getId());
        assert flag;
        Long count2 = courseMapper.selectCount(null);
        assert count1 == count2 + 1;
    }

    @Test
    void getCourseByTeacherNameCourseName() {
        setUp();
        Course course = courseService.getOneCourse(allCourses.get(0).getId());
        assert course.getCourseName().equals("rc1_t1");
        System.out.println(course);
        tearDown();
    }

    @Test
    void getCoursePrivateKeyUrl() {
        User teacher1 = new User("teacher1", UserType.Teacher, "teacher1@mail", "123456");
        userMapper.insert(teacher1);
        Course rc1_t1 = new Course("rc1_t1", teacher1.getId(), "test", 0L, "review", CourseStatus.REVIEWING, "assign_url");
        rc1_t1.setPrivateKeyUrl("privatekey");
        rc1_t1.setTeacherName(teacher1.getUserName());
        courseMapper.insert(rc1_t1);
        String key = courseService.getCoursePrivateKeyUrl(teacher1.getUserName(), rc1_t1.getCourseName());
        assert key.equals("privatekey");
        rc1_t1.setStatus(CourseStatus.APPROVED);
        courseService.updateCourse(rc1_t1);
        key = courseService.getCoursePrivateKeyUrl(teacher1.getUserName(), rc1_t1.getCourseName());
        assert key.equals("privatekey");
        rc1_t1.setPrivateKeyUrl("new pr key");
        courseService.updateCourse(rc1_t1);
        key= courseService.getCoursePrivateKeyUrl(teacher1.getUserName(), rc1_t1.getCourseName());
        assert key.equals("new pr key");
        courseMapper.deleteById(rc1_t1);
        userMapper.deleteById(teacher1);
    }

    @Test
    void getApprovedCoursesByCourseName() {
        setUp();
        Course c1 = allCourses.get(0);
        System.out.println(c1.getCourseName());
        List<Course> courses = courseService.getApprovedCourseList(10, 1, c1.getCourseName());
        System.out.println(courses.size());
        courses.forEach(System.out::println);
        assert courses.size() == 2;
        tearDown();
    }
}
