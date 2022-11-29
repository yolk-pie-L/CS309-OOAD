package com.example.live_video.controller;

import com.example.live_video.dto.CourseDto;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.StudentService;
import com.example.live_video.vo.CourseVo;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ResponseResult
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/api/course/success")
    List<CourseVo> getAllSuccessCourseByTeacher(@RequestParam int recordsPerPage,
                                                @RequestParam int pageNum,
                                                @RequestParam(required = false) String courseName,
                                                @RequestParam(required = false) String teacherName) {
        // FIXME: Two methods may wrong
        if (StringUtils.hasText(courseName) && StringUtils.hasText(teacherName)) {
            return CourseVo.parse(courseService.getApprovedCoursesByCourseName(courseName));
        }
        if (StringUtils.hasText(courseName)) {
            return CourseVo.parse(courseService.getApprovedCoursesByCourseName(courseName));
        }
        if (StringUtils.hasText(teacherName)) {
            return CourseVo.parse(courseService.getApprovedCoursesOfTeacher(recordsPerPage, pageNum, teacherName));
        }
        return CourseVo.parse(courseService.getApprovedCourses(recordsPerPage, pageNum));
    }

    @GetMapping("/api/course/all")
    public CourseVo queryAllCourseByUsername(@RequestParam String userName) {
        return CourseVo.parseEasy(courseService.getCourseByTeacherNameCourseName(userName, null));
    }

    @PostMapping ("/api/course")
    public Boolean modifyCourseOfTeacher(@RequestBody CourseDto courseDto) {
        return courseService.updateCourse(courseDto.convertToCourse(CourseStatus.REVIEWING));
    }

    @GetMapping("/api/course/waiting")
    public List<CourseVo> queryCourseOfAdministrator(@RequestParam String administrator) {
        return CourseVo.parse(courseService.getReviewingCourses());
    }

    @PostMapping("api/course/admin")
    public Boolean updateCourseStatus(@RequestParam String courseName,
                                      @RequestParam String courseStatus) {
        /* TODO: Maybe we should have a method called updateStatusByCourseName?
        return courseService.updateStatusByCourseName(courseName, courseStatus);
        */
        return null;
    }

    @PostMapping("api/enroll-course")
    public Boolean enroll(@RequestParam String studentName,
                          @RequestParam String courseName,
                          @RequestParam String teacherName) throws Exception {
        return studentService.enrollCourse(teacherName, courseName, studentName);
    }

}
