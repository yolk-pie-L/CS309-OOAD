package com.example.live_video.service;

import com.example.live_video.entity.*;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.SectionMapper;
import com.example.live_video.mapper.UserMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class CommentServiceTest {

    @Autowired
    CommentService commentService;

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
        User teacher1 = new User("user23", UserType.Teacher, "user23@mail.com1", "123456");
        userMapper.insert(teacher);
        userMapper.insert(teacher1);
        allUsers.add(teacher);
        allUsers.add(teacher1);
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

    @Test
    void getCommentList() {
    }


    @Test
    void saveComment() throws IOException {
        Section section1 = allSections.get(0);
        User user1 = allUsers.get(0);
        User user2 = allUsers.get(1);
        Comment comment = new Comment("root1", user1.getId(), section1.getId());
        commentService.saveComment(comment);
        Comment comment2 = new Comment(comment.getId(), "root1_level1", user1.getId(), section1.getId());
        commentService.saveComment(comment2);
        Comment comment3 = new Comment(comment2.getId(), "root1_level2", user1.getId(), section1.getId());
        commentService.saveComment(comment3);
        Comment comment4 = new Comment(comment3.getId(), "root1_level3", user2.getId(), section1.getId());
        commentService.saveComment(comment4);
        Comment comment5 = new Comment(comment.getId(), "root_level1", user1.getId(), section1.getId());
        commentService.saveComment(comment5);
        Comment comment6 = new Comment(comment5.getParentId(), "root_level2", user2.getId(), section1.getId());
        commentService.saveComment(comment6);
        Comment comment1 = new Comment("root2", user1.getId(), section1.getId());
        commentService.saveComment(comment1);
        Comment comment7 = new Comment(comment1.getId(), "root2_level1", user2.getId(), section1.getId());
        commentService.saveComment(comment7);
        Section section2 = allSections.get(1);
        Comment comment8 = new Comment("section2_root1", user1.getId(), section2.getId());
        commentService.saveComment(comment8);
        Comment comment9 = new Comment(comment8.getId(), "s2_root1_level1", user2.getId(), section2.getId());
        commentService.saveComment(comment9);
        List<Comment> comments = commentService.getCommentList(section1.getId());
        Gson gson = new Gson();
        String pjson = gson.toJson(comments);
        File file = new File("src/test/java/com/example/live_video/service/test.json");
        file.createNewFile();
        FileWriter fw = new FileWriter(file);
        fw.write(pjson);
        fw.close();
        commentService.deleteComment(comment.getId());
        List<Comment> comments1 = commentService.getCommentList(section1.getId());
        pjson = gson.toJson(comments1);
        file = new File("src/test/java/com/example/live_video/service/test2.json");
        file.createNewFile();
        fw = new FileWriter(file);
        fw.write(pjson);
        fw.close();
    }
}