package com.example.live_video.controller;

import com.example.live_video.service.AssignmentService;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.UserService;
import com.example.live_video.vo.AssignmentVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/assignment")
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/all")
    public List<AssignmentVo> queryAssignmentByCourseName(@RequestParam("courseId") String courseId) {
//        Course course = courseService.getOneCourse(Long.parseLong(courseId));
//        String courseName = course.getCourseName();
//        String teacherName = course.getTeacherName();
//        if (teacherName == null) teacherName = userService.getById(course.getTeacherId()).getUserName();
//        List<Assignment> assignmentList = assignmentService.getAssignmentByCourse(courseName, teacherName);
//        return assignmentList;
        return AssignmentVo.parseInfo(assignmentService.getAssignmentsOfCourse(Long.parseLong(courseId)));
    }

    @GetMapping("/one")
    public AssignmentVo queryAssignmentById(@RequestParam("assignmentId") String assignmentId) {
        Long userId = 1L;  // fixme
        return null;
    }

    @PostMapping("/submit")
    public boolean submitAssignment() {
        return false;
    }

    @PostMapping("/upload")
    public String uploadFile() {
        return "";
    }

    @PostMapping("/create")
    public boolean createAssignment() {
        return false;
    }


}
