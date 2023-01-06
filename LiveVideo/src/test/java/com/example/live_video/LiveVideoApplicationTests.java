package com.example.live_video;

import com.example.live_video.entity.*;
import com.example.live_video.exception.EnrollCourseException;
import com.example.live_video.exception.MyException;
import com.example.live_video.mapper.*;
import com.example.live_video.service.SectionService;
import com.example.live_video.service.StudentService;
import com.example.live_video.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @Autowired
    SectionService sectionService;

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    @Test
    void contextLoads() throws MyException {
        String defaultUrlPath = "default.png";
        User user = new User("LIXIN", UserType.Administrator, "lx@mail.com1", DigestUtils.md5DigestAsHex("123456LIXIN".getBytes()), defaultUrlPath, 0L);
        User chenruiyao = new User("CHENRUIYAO", UserType.Student, "cry@mail.com1", DigestUtils.md5DigestAsHex("23EDCXCHENRUIYAO".getBytes()), defaultUrlPath, 1000L);
        User wangzian = new User("WANGZIAN", UserType.Student, "wza@mail.com1", DigestUtils.md5DigestAsHex("23EDCXWANGZIAN".getBytes()), defaultUrlPath, 2000L);
        User likai = new User("LIKAI", UserType.Student, "12013029@mail.sustech.edu.cn", DigestUtils.md5DigestAsHex("123456LIKAI".getBytes()), defaultUrlPath, 3000L);
        User suzhaowen = new User("SUZHAOWEN", UserType.Student, "szw@mail.com1", DigestUtils.md5DigestAsHex("123456SUZHAOWEN".getBytes()), defaultUrlPath, 3000L);
        User wangchenyu = new User("WANGCHENYU", UserType.Student, "wcy@mail.com1", DigestUtils.md5DigestAsHex("34rfsvWANGCHENYU".getBytes()), defaultUrlPath, 3000L);
        User lzz = new User("LZZ", UserType.Teacher, "lzz@mail.com1", DigestUtils.md5DigestAsHex("23EDCXLZZ".getBytes()), defaultUrlPath, 3000L);
        User sy = new User("SY", UserType.Teacher, "sy@mail.com1", DigestUtils.md5DigestAsHex("123456SY".getBytes()), defaultUrlPath, 3000L);
        User hq = new User("HQ", UserType.Teacher, "hq@mail.com1", DigestUtils.md5DigestAsHex("123456HQ".getBytes()), defaultUrlPath, 3000L);
        User lfl = new User("LFL", UserType.Teacher, "lfl@mail.com1", DigestUtils.md5DigestAsHex("sdfwadseLFL".getBytes()), defaultUrlPath, 3000L);
        userMapper.insert(user);
        userMapper.insert(chenruiyao);
        userMapper.insert(wangzian);
        userMapper.insert(likai);
        userMapper.insert(suzhaowen);
        userMapper.insert(wangchenyu);
        userMapper.insert(lzz);
        userMapper.insert(sy);
        userMapper.insert(hq);
        userMapper.insert(lfl);
        Course ComputerNetwork = new Course("Computer Network", lzz.getId(), "CS", 10L, "good", CourseStatus.APPROVED, "课程示例图.jpg");
        Course DataStructure = new Course("Data Structure", sy.getId(), "CS", 20L, "good", CourseStatus.APPROVED, "课程示例图.jpg");
        Course MachineLearning = new Course("Machine Learning", hq.getId(), "ML", 30L, "bad", CourseStatus.APPROVED, "课程示例图.jpg");
        Course Politics = new Course("Politics", lfl.getId(), "POL", 40L, "bad", CourseStatus.APPROVED, "课程示例图.jpg");
        Course JAVA_A_lzz = new Course("JAVA A", lzz.getId(), "CS", 50L, "good", CourseStatus.APPROVED, "课程示例图.jpg");
        Course JAVA_A_sy = new Course("JAVA A", sy.getId(), "CS", 0L, "good", CourseStatus.APPROVED, "课程示例图.jpg");
        Course ROBOTS = new Course("ROBOTS", hq.getId(), "ME", 3L, "unknown", CourseStatus.FAILED, "课程示例图.jpg");
        Course Operating_System = new Course("Operating System", sy.getId(), "CS", 23L, "sy is god", CourseStatus.REVIEWING, "课程示例图.jpg");
        Course MathematicalLogic = new Course("Mathematical Logic", hq.getId(), "MA", 0L, "hard", CourseStatus.APPROVED, "课程示例图.jpg");
        Course JAVA_B = new Course("JAVA B", lzz.getId(), "CS", 8L, "easy", CourseStatus.REVIEWING, "课程示例图.jpg");
        courseMapper.insert(ComputerNetwork);
        courseMapper.insert(DataStructure);
        courseMapper.insert(MachineLearning);
        courseMapper.insert(Politics);
        courseMapper.insert(JAVA_A_lzz);
        courseMapper.insert(JAVA_A_sy);
        courseMapper.insert(ROBOTS);
        courseMapper.insert(Operating_System);
        courseMapper.insert(MathematicalLogic);
        courseMapper.insert(JAVA_B);
        Section section1 = new Section("LinkedList", DataStructure.getId(), "s1", 99);
        Section section2 = new Section("Queue", DataStructure.getId(), "s2", 88);
        Section section3 = new Section("Stack", DataStructure.getId(), "s3", 77);
        Section section4 = new Section("CNN", MachineLearning.getId(), "s4", 60);
        Section section5 = new Section("GAN", MachineLearning.getId(), "s5", 50);
        Section section6 = new Section("China", Politics.getId(), "s6", 40);
        Section section7 = new Section("MaoZeDong", Politics.getId(), "s4", 100);
        Section section8 = new Section("XiJinPing", Politics.getId(), "s5", 20);
        Section section9 = new Section("DengXiaoPing", Politics.getId(), "s6", 10);
        Section section10 = new Section("Method", JAVA_A_lzz.getId(), "s6", 39);
        Section section11 = new Section("Class", JAVA_A_lzz.getId(), "s4", 24);
        Section section12 = new Section("PrimitiveType", JAVA_A_lzz.getId(), "s5", 333);
        Section section13 = new Section("GetterSetter", JAVA_A_lzz.getId(), "s6", 234);
        Section section14 = new Section("Enum", JAVA_A_lzz.getId(), "s4", 25);
        Section section15 = new Section("AbstractClass", JAVA_A_lzz.getId(), "s5", 24);
        Section linkedlayer = new Section("LinkedLayer", ComputerNetwork.getId(), "s6", 60);
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
        sectionMapper.insert(linkedlayer);
        Notice n1 = new Notice("project1 released", ComputerNetwork.getId(), "project1 released, ddl is tomorrow");
        Notice n2 = new Notice("project2 released", ComputerNetwork.getId(), "project2 released, ddl is today");
        Notice n3 = new Notice("OJ account", DataStructure.getId(), "check your oj account");
        Notice n4 = new Notice("Bonus", DataStructure.getId(), "everybody add 3 point");
        Notice n5 = new Notice("Midterm exam", DataStructure.getId(), "very easy");
        Notice n6 = new Notice("Quizes", DataStructure.getId(), "every student can get full score");
        noticeMapper.insert(n1);
        noticeMapper.insert(n2);
        noticeMapper.insert(n3);
        noticeMapper.insert(n4);
        noticeMapper.insert(n5);
        noticeMapper.insert(n6);
        List<String> urls = new ArrayList<>();
        urls.add("url1");
        urls.add("url2");
        Assignment dns = new Assignment("dns", ComputerNetwork.getId(), null, 100, urls, true, "dns local server");
        Assignment dijkstra = new Assignment("dijkstra", ComputerNetwork.getId(), null, 100, null, true, "write an algorithm in python");
        Assignment websocket = new Assignment("websocket", ComputerNetwork.getId(), null, 100, null, true, "user websocket to implement danmu");
        Assignment queue = new Assignment("queue practice", DataStructure.getId(), null, 100, null, false, "little quiz");
        assignmentMapper.insert(dns);
        assignmentMapper.insert(dijkstra);
        assignmentMapper.insert(websocket);
        assignmentMapper.insert(queue);
        studentService.enrollCourse(ComputerNetwork.getId(), chenruiyao.getUserName());
        studentService.enrollCourse(DataStructure.getId(), chenruiyao.getUserName());
        studentService.enrollCourse(ComputerNetwork.getId(), likai.getUserName());
        studentService.enrollCourse(DataStructure.getId(), likai.getUserName());
        studentService.enrollCourse(JAVA_A_lzz.getId(), wangzian.getUserName());
        studentService.setStudentAssignGrade(chenruiyao.getUserName(), dns.getId(), 100);
        studentService.setStudentAssignGrade(likai.getUserName(), dns.getId(), 110);
        studentService.setStudentAssignGrade(chenruiyao.getUserName(), dijkstra.getId(), 80);
        studentService.setStudentAssignGrade(likai.getUserName(), dijkstra.getId(), 88);
        studentService.setStudentSectionProgress(chenruiyao.getId(), linkedlayer.getId(), 0.8);
        studentService.setStudentSectionProgress(likai.getId(), linkedlayer.getId(), 0.3);
    }

    @Test
    void testSectionCache() {
        Long sectionId = 1L;
        System.out.println();
        System.out.println();
        System.out.println("----!!Get One Section!! Should only print once!!----");
        System.out.println();
        System.out.println();
        Section section = sectionService.getOneSection(sectionId);
        System.out.println();
        System.out.println();
        Section section1 = sectionService.getOneSection(sectionId);
        assert section1.getId().equals(section.getId());
        assert section1.getGrade() == section.getGrade();
        assert Objects.equals(section1.getCourseId(), section.getCourseId());
        System.out.println("=====================================================");
        System.out.println("----!!Update Section!! Should only print once!!----");
        section.setVideoUrl("new url");
        sectionService.updateSection(section);
        section = sectionService.getOneSection(sectionId);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        section1 = sectionService.getOneSection(sectionId);
        assertEquals("new url", section1.getVideoUrl());
        assert section1.getGrade() == section.getGrade();
        System.out.println("===============================================");
        System.out.println("--------Remove Section-------------------------");
        sectionService.removeSection(sectionId);
        Section section2 = sectionService.getOneSection(sectionId);
        assertNull(section2);
        System.out.println("=================================================");
    }
}
