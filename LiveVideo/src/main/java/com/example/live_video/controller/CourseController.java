package com.example.live_video.controller;

import com.example.live_video.dto.CourseDto;
import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.exception.SQLCoursenameConflictException;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.StudentService;
import com.example.live_video.vo.CourseVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import com.example.live_video.wrapper.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ResponseResult
@RestController
@UserLoginToken
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/success")
    List<CourseVo> getAllSuccessCourseByTeacher(@RequestParam int recordsPerPage,
                                                @RequestParam int pageNum,
                                                @RequestParam(required = false) String courseName,
                                                @RequestParam(required = false) String teacherName) {
        if (StringUtils.hasText(courseName) && StringUtils.hasText(teacherName)) {
            return Collections.singletonList(CourseVo.parse(courseService.getOneApprovedCourse(teacherName, courseName)));
        }
        if (StringUtils.hasText(courseName)) {
            return CourseVo.parse(courseService.getApprovedCourseList(recordsPerPage, pageNum, courseName));
        }
        if (StringUtils.hasText(teacherName)) {
            return CourseVo.parse(courseService.getApprovedCourseListOfTeacher(recordsPerPage, pageNum, teacherName));
        }
        return CourseVo.parse(courseService.getApprovedCourseList(recordsPerPage, pageNum));
    }

    @GetMapping("/all{userName}")
    public List<CourseVo> queryAllCourseByUsername(@PathVariable String userName) {
        // FIXME(by Li Kai to Li Xin): It contains two type, one for student, one for teacher. And It should return a list of course.
//        return CourseVo.parseEasy(courseService.getOneCourse(userName, null));
        return null;
    }

    @PostMapping ("")
    public Boolean modifyCourseOfTeacher(@RequestBody CourseDto courseDto) {
        return courseService.updateCourse(courseDto.convertToCourse(CourseStatus.REVIEWING));
    }

    @PostMapping ("insert")
    public Boolean insertCourseOfTeacher(@RequestBody CourseDto courseDto) throws SQLCoursenameConflictException {
        return courseService.createCourse(courseDto.convertToCourse(CourseStatus.REVIEWING));
    }

    @GetMapping("waiting")
    public List<CourseVo> queryCourseOfAdministrator(@RequestParam String administrator) {
        return CourseVo.parse(courseService.getReviewingCourseList());
    }

    @PostMapping("admin")
    public Boolean updateCourseStatus(@RequestParam String teacherName,
                                      @RequestParam String courseName,
                                      @RequestParam String courseStatus) {
        Course course = courseService.getOneCourse(teacherName, courseName);
        course.setStatus(CourseStatus.valueOf(courseStatus));
        courseService.updateCourse(course);
        return true;
    }

    @PostMapping("enroll")
    public Boolean enroll(@RequestParam String studentName,
                          @RequestParam String courseName,
                          @RequestParam String teacherName) throws Exception {
        return studentService.enrollCourse(teacherName, courseName, studentName);
    }

    @PostMapping("exit")
    public Boolean exit(@RequestParam String studentName,
                        @RequestParam String courseName,
                        @RequestParam String teacherName) throws Exception {
        return studentService.exitCourse(teacherName, courseName, studentName);
    }
}
