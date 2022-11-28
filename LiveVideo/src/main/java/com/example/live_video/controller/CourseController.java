package com.example.live_video.controller;

import com.example.live_video.dto.CourseDto;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.StudentService;
import com.example.live_video.vo.CourseVo;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                                                @RequestParam(required = false) String courseName) {
        if (courseName == null)
            return CourseVo.parse(courseService.getApprovedCourses(recordsPerPage, pageNum));
        else {
            // FIXME: Maybe it is courseName but not teacherName?
            return CourseVo.parse(courseService.getApprovedCoursesOfTeacher(recordsPerPage, pageNum, courseName));
        }
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
        //TODO: return CourseVo.parse(courseService.getReviewingCourses());
        return null;
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
