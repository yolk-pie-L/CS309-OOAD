package com.example.live_video.mapper;

import com.example.live_video.entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback
public class SectionMapperTest {

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
        Course course1 = new Course("rc1_t1", teacher.getId(), "test", 0L, "review", CourseStatus.REVIEWING, "assign_url");
        course1.setTeacherName(teacher.getUserName());
        Course course2 = new Course("rc2_t1", teacher.getId(), "test", 0L, "review", CourseStatus.REVIEWING, "assign_url");
        course2.setTeacherName(teacher.getUserName());
        courseMapper.insert(course1);
        courseMapper.insert(course2);
        allCourses.add(course1);
        allCourses.add(course2);
        Section section1 = new Section("s1", course1.getCourseName(), teacher.getUserName(), "assign_url");
        Section section2 = new Section("s2", course1.getCourseName(), teacher.getUserName(), "assign_url");
        Section section3 = new Section("s3", course1.getCourseName(), teacher.getUserName(), "assign_url");
        Section section4 = new Section("s4", course2.getCourseName(), teacher.getUserName(), "assign_url");
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
    void selectAll(){
        List<Section> sectionList = sectionMapper.selectList(null);
        sectionList.forEach(System.out::println);
    }
}
