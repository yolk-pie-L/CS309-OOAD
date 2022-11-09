package com.example.live_video.service;

import com.example.live_video.entity.*;
import com.example.live_video.exception.MyException;
import com.example.live_video.exception.SQLSectionnameConflictException;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.SectionMapper;
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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class SectionServiceTest {

    @Autowired
    SectionService sectionService;

    @Autowired
    SectionMapper sectionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CourseMapper courseMapper;

    List<User> allUsers = new ArrayList<>();

    List<Course> allCourses = new ArrayList<>();

    List<Section> allSections = new ArrayList<>();

    @BeforeEach
    void setUp() {
        User teacher = new User("user2", UserType.Teacher, "user2@mail.com1", "123456");
        userMapper.insert(teacher);
        allUsers.add(teacher);
        Course course1 = new Course("rc1_t1", teacher.getId(), "test", 0L, "review", CourseStatus.REVIEWING, "url");
        course1.setTeacherName(teacher.getUserName());
        Course course2 = new Course("rc2_t1", teacher.getId(), "test", 0L, "review", CourseStatus.REVIEWING, "url");
        course2.setTeacherName(teacher.getUserName());
        courseMapper.insert(course1);
        courseMapper.insert(course2);
        allCourses.add(course1);
        allCourses.add(course2);
        Section section1 = new Section("s1", course1.getCourseName(), teacher.getUserName(), "url");
        Section section2 = new Section("s2", course1.getCourseName(), teacher.getUserName(), "url");
        Section section3 = new Section("s3", course1.getCourseName(), teacher.getUserName(), "url");
        Section section4 = new Section("s4", course2.getCourseName(), teacher.getUserName(), "url");
        section1.setCourseId(course1.getId());
        section2.setCourseId(course1.getId());
        section3.setCourseId(course1.getId());
        section4.setCourseId(course2.getId());
        sectionMapper.insert(section1);
        sectionMapper.insert(section2);
        sectionMapper.insert(section3);
        sectionMapper.insert(section4);
        allSections.add(section1);
        allSections.add(section2);
        allSections.add(section3);
        allSections.add(section4);
    }

    @AfterEach
    void tearDown() {
        sectionMapper.deleteBatchIds(allSections);
        courseMapper.deleteBatchIds(allCourses);
        userMapper.deleteBatchIds(allUsers);
    }

    @Test
    void createSection() {
        Course course2 = allCourses.get(1);
        User teacher = allUsers.get(0);
        Section section5 = new Section("s5", course2.getCourseName(), teacher.getUserName(), "url");
        boolean flag = false;
        try {
            flag = sectionService.createSection(section5);
        }catch (MyException e){
            flag = false;
        }
        assert flag;
        try {
            sectionService.createSection(section5);
            flag = false;
        }catch (MyException e){
            flag = e instanceof SQLSectionnameConflictException;
        }
        assert flag;
        sectionMapper.deleteById(section5);
    }

    @Test
    void updateSection() {
        Course course1 = allCourses.get(0);
        User teacher = allUsers.get(0);
        Section ansSection = allSections.get(0);
        Section section1 = new Section("s1", course1.getCourseName(), teacher.getUserName(), null);
        sectionService.updateSection(section1);
        Section section = sectionMapper.selectById(ansSection.getId());
        assert Objects.equals(section.getVideoUrl(), ansSection.getVideoUrl());
        section1.setVideoUrl("new url");
        sectionService.updateSection(section1);
        section = sectionMapper.selectById(ansSection.getId());
        assert Objects.equals(section.getVideoUrl(), section1.getVideoUrl());
    }

    @Test
    void removeSection() {
        Section testSection = allSections.get(0);
        long count = sectionMapper.selectCount(null);
        sectionService.removeSection(testSection.getTeacherName(), testSection.getCourseName(), testSection.getSectionName());
        long count2 = sectionMapper.selectCount(null);
        assert count2 == count - 1;
        List<Section> sectionList = sectionMapper.selectList(null);
        for(Section section: sectionList){
            assert !Objects.equals(section.getSectionName(), testSection.getSectionName());
        }
        allSections.remove(testSection);
    }
}