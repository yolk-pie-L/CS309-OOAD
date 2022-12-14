package com.example.live_video.service;

import com.example.live_video.entity.*;
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

@Transactional
@Rollback
@SpringBootTest
class NoticeServiceTest {

    @Autowired
    NoticeService noticeService;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    UserMapper userMapper;

    User teacher = null;
    Course course = null;
    Course course1 = null;

    List<Notice> noticeList = new ArrayList<>();

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createNotice() {
        long count = noticeService.count();
        System.out.printf("count %d\n", count);
        teacher = new User("teacher1", UserType.Teacher, "teacher1@mail", "123456");
        userMapper.insert(teacher);
        course = new Course("rc1_t1", teacher.getId(), "test", 0L, "review", CourseStatus.REVIEWING, "assign_url");
        course1 = new Course("rc2_t1", teacher.getId(), "test", 0L, "review", CourseStatus.REVIEWING, "assign_url");
        courseMapper.insert(course);
        courseMapper.insert(course1);


        Notice n1 = new Notice( "n1",course.getId(), "blabla");
        Notice n2 = new Notice("n2", course.getId(), "blabla");
        Notice n3 = new Notice("n3", course1.getId(), "ahahaha");
        noticeList.add(n1);
        noticeList.add(n2);
        noticeList.add(n3);
        noticeList.forEach(n -> noticeService.createNotice(n, false));
        List<Notice> notices1 = noticeService.getNoticeListOfCourse(course.getId());
        List<Notice> notices2 = noticeService.getNoticeListOfCourse(course1.getId());
        assert notices1.size() == 2;
        assert notices2.size() == 1;
        assert notices1.get(0).getNoticeName().equals("n1");
        noticeService.deleteNotice(notices1.get(0).getId());
        noticeService.deleteNotice(notices1.get(1).getId());
        noticeService.deleteNotice(notices2.get(0).getId());
        long count2 = noticeService.count();
        assert count2 == count;


        courseMapper.deleteById(course);
        courseMapper.deleteById(course1);
        userMapper.deleteById(teacher);
    }

    @Test
    void getNoticeListOfCourse() {
    }

    @Test
    void deleteNotice() {
    }
}