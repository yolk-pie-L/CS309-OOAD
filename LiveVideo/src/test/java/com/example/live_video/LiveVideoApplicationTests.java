package com.example.live_video;

import com.example.live_video.entity.*;
import com.example.live_video.mapper.*;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class LiveVideoApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    SectionMapper sectionMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    AssignmentMapper assignmentMapper;

    @Test
    void contextLoads() {
        User user = new User("CHENRUIYAO", UserType.Student, "cry@mail.com1", "23EDCX");
        User user1 = new User("WANGZIAN", UserType.Student, "wza@mail.com1", "23EDCX");
        User user2 = new User("LIXIN", UserType.Administrator, "lx@mail.com1", "123456");
        User user3 = new User("LIKAI", UserType.Administrator, "lk@mail.com1", "123456");
        User user4 = new User("SUZHAOWEN", UserType.Student, "szw@mail.com1", "123456");
        User user5 = new User("LZZ", UserType.Teacher, "lzz@mail.com1", "23EDCX");
        User user6 = new User("SY", UserType.Teacher, "sy@mail.com1", "123456");
        User user7 = new User("HQ", UserType.Teacher, "hq@mail.com1", "123456");
        User user8 = new User("LFL", UserType.Teacher, "lfl@mail.com1", "sdfwadse");
        User user9 = new User("WANGCHENYU", UserType.Student, "wcy@mail.com1", "34rfsv");
        userMapper.insert(user);
        userMapper.insert(user1);
        userMapper.insert(user2);
        userMapper.insert(user3);
        userMapper.insert(user4);
        userMapper.insert(user5);
        userMapper.insert(user6);
        userMapper.insert(user7);
        userMapper.insert(user8);
        userMapper.insert(user9);
        Course course = new Course("Computer Network", user5.getId(), "test", 10L, "review", CourseStatus.REVIEWING, "assign_url");
        Course course1 = new Course("Data Structure", user6.getId(), "test", 20L, "app", CourseStatus.APPROVED, "assign_url");
        Course course2 = new Course("Machine Learning", user7.getId(), "test", 30L, "app", CourseStatus.APPROVED, "assign_url");
        Course course3 = new Course("Politics", user8.getId(), "EE", 40L, "rev", CourseStatus.REVIEWING, "assign_url");
        Course course4 = new Course("JAVA A", user5.getId(), "CSEE", 50L, "review", CourseStatus.REVIEWING, "assign_url");
        Course course5 = new Course("JAVA A", user6.getId(), "CSE", 0L, "approve", CourseStatus.APPROVED, "assign_url");
        Course course6 = new Course("ROBOTS", user7.getId(), "CS", 3L, "fail", CourseStatus.FAILED, "assign_url");
        Course course7 = new Course("OPERATING SYSTEM", user6.getId(), "CS", 23L, "rev", CourseStatus.REVIEWING, "assign_url");
        Course course8 = new Course("MATHEMATICAL LOGIC", user7.getId(), "test", 0L, "app", CourseStatus.APPROVED, "assign_url");
        Course course9 = new Course("JAVA B", user5.getId(), "test", 8L, "rev", CourseStatus.REVIEWING, "assign_url");
        courseMapper.insert(course);
        courseMapper.insert(course1);
        courseMapper.insert(course2);
        courseMapper.insert(course3);
        courseMapper.insert(course4);
        courseMapper.insert(course5);
        courseMapper.insert(course6);
        courseMapper.insert(course7);
        courseMapper.insert(course8);
        courseMapper.insert(course9);
        Section section1 = new Section("s1", course1.getId(), "s1");
        Section section2 = new Section("s2", course1.getId(), "s2");
        Section section3 = new Section("s3", course1.getId(), "s3");
        Section section4 = new Section("s4", course2.getId(), "s4");
        Section section5 = new Section("s5", course2.getId(), "s5");
        Section section6 = new Section("s6", course3.getId(), "s6");
        Section section7 = new Section("s7", course3.getId(), "s4");
        Section section8 = new Section("s8", course3.getId(), "s5");
        Section section9 = new Section("s9", course3.getId(), "s6");
        Section section10 = new Section("s1", course4.getId(), "s6");
        Section section11 = new Section("s2", course4.getId(), "s4");
        Section section12 = new Section("s3", course4.getId(), "s5");
        Section section13 = new Section("s4", course4.getId(), "s6");
        Section section14 = new Section("s5", course4.getId(), "s4");
        Section section15 = new Section("s6", course4.getId(), "s5");
        Section section16 = new Section("s7", course4.getId(), "s6");
        sectionMapper.insert(section1);
        sectionMapper.insert(section2);
        sectionMapper.insert(section3);
        sectionMapper.insert(section4);
        sectionMapper.insert(section5);
        sectionMapper.insert(section6);
        sectionMapper.insert(section7);
        sectionMapper.insert(section8);
        sectionMapper.insert(section9);
        sectionMapper.insert(section10);
        sectionMapper.insert(section11);
        sectionMapper.insert(section12);
        sectionMapper.insert(section13);
        sectionMapper.insert(section14);
        sectionMapper.insert(section15);
        sectionMapper.insert(section16);
        Notice n1 = new Notice( "n1",course.getId(), "blabla");
        Notice n2 = new Notice("n2", course.getId(), "blabla");
        Notice n3 = new Notice("n3", course1.getId(), "ahahaha");
        Notice n4 = new Notice("n4", course1.getId(), "hdasdf");
        Notice n5 = new Notice("hurry up be quick LIKAI!", course1.getId(), "kai!");
        Notice n6 = new Notice("LIKAI IS GOOD", course1.getId(), "liaki \n is a good student");
        noticeMapper.insert(n1);
        noticeMapper.insert(n2);
        noticeMapper.insert(n3);
        noticeMapper.insert(n4);
        noticeMapper.insert(n5);
        noticeMapper.insert(n6);
    }

}
