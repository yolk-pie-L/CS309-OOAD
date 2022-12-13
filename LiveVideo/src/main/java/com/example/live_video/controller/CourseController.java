package com.example.live_video.controller;

import com.example.live_video.dto.CourseDto;
import com.example.live_video.dto.JSONObject;
import com.example.live_video.dto.JoinForm;
import com.example.live_video.dto.UserForm;
import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.SQLCoursenameConflictException;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.StudentService;
import com.example.live_video.service.UserService;
import com.example.live_video.vo.CourseVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import com.example.live_video.wrapper.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/{courseId}")
    CourseVo getCourse(@PathVariable int courseId) {
        return CourseVo.parse(courseService.getOneCourse((long) courseId));
    }

    @GetMapping("/success/all")
    List<CourseVo> getAllSuccessCourseByTeacher(@RequestParam int o,
                                                @RequestParam int page,
                                                @RequestParam(required = false) String courseName,
                                                @RequestParam(required = false) String teacherName) {
        if (StringUtils.hasText(courseName) && StringUtils.hasText(teacherName)) {
            return Collections.singletonList(CourseVo.parse(courseService.getOneApprovedCourse(teacherName, courseName)));
        }
        if (StringUtils.hasText(courseName)) {
            return CourseVo.parse(courseService.getApprovedCourseList(o, page, courseName));
        }
        if (StringUtils.hasText(teacherName)) {
            return CourseVo.parse(courseService.getApprovedCourseListOfTeacher(o, page, teacherName));
        }
        return CourseVo.parse(courseService.getApprovedCourseList(o, page));
    }

    @GetMapping("/all")
    public List<CourseVo> queryAllCourseByUsername(@RequestParam String userName,
                                                   @RequestParam(required = false) int page,
                                                   @RequestParam(required = false) int o) {
        if (userService.getUserType(userName) == UserType.Student) {
            return CourseVo.parse(studentService.getEnrolledCourseList(userName));
        }
        if (userService.getUserType(userName) == UserType.Teacher) {
            return CourseVo.parse(courseService.getApprovedCourseListOfTeacher(o, page, userName));
        }
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
    public Boolean enroll(@RequestBody JoinForm joinForm) throws Exception {
        Long courseId = joinForm.getCourseId();
        String studentName = joinForm.getStudentName();
        System.out.println(joinForm);
        return studentService.enrollCourse(courseId, studentName);
    }

    @PostMapping("exit")
    public Boolean exit(@RequestBody JoinForm joinForm) throws Exception {
        Long courseId = joinForm.getCourseId();
        String studentName = joinForm.getStudentName();
        System.out.println(joinForm);
        return studentService.exitCourse(courseId, studentName);
    }
}
