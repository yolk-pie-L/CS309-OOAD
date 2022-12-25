package com.example.live_video.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class StudentMapperTest {

    @Autowired
    StudentMapper studentMapper;

    @Test
    void enrollCourse() {
        studentMapper.enrollCourse(1L, 3L);
    }

    @Test
    void getEnrolledCourseStudentEmailList() {
        String[] res = studentMapper.getEnrolledCourseStudentEmailList(1L);
        System.out.println(Arrays.toString(res));
    }
}