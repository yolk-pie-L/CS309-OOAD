package com.example.live_video.controller;

import com.example.live_video.dto.CourseDto;
import com.example.live_video.entity.Course;
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
        if (StringUtils.hasText(courseName) && StringUtils.hasText(teacherName)) {
            //FIXME: perhaps not elegant?
            List<Course> courses = new ArrayList<>();
            courses.add(courseService.getOneApprovedCourse(teacherName, courseName));
            return CourseVo.parse(courses);
        }
        if (StringUtils.hasText(courseName)) {
            return CourseVo.parse(courseService.getApprovedCourseList(recordsPerPage, pageNum, courseName));
        }
        if (StringUtils.hasText(teacherName)) {
            return CourseVo.parse(courseService.getApprovedCourseListOfTeacher(recordsPerPage, pageNum, teacherName));
        }
        return CourseVo.parse(courseService.getApprovedCourseList(recordsPerPage, pageNum));
    }

    @GetMapping("/api/course/all")
    public CourseVo queryAllCourseByUsername(@RequestParam String userName) {
        return CourseVo.parseEasy(courseService.getOneCourse(userName, null));
    }

    @PostMapping ("/api/course")
    public Boolean modifyCourseOfTeacher(@RequestBody CourseDto courseDto) {
        return courseService.updateCourse(courseDto.convertToCourse(CourseStatus.REVIEWING));
    }

    @GetMapping("/api/course/waiting")
    public List<CourseVo> queryCourseOfAdministrator(@RequestParam String administrator) {
        return CourseVo.parse(courseService.getReviewingCourseList());
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
                          @RequestParam String courseName) throws Exception {
        // TODO: 也许不用提供老师名字？
        return studentService.enrollCourse(null, courseName, studentName);
    }

}
