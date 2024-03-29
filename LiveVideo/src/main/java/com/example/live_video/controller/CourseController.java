package com.example.live_video.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.live_video.dto.CourseDto;
import com.example.live_video.dto.JoinForm;
import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.MyException;
import com.example.live_video.exception.SQLCoursenameConflictException;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.StudentMapper;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.StudentService;
import com.example.live_video.service.UserService;
import com.example.live_video.util.FileWithExcelUtil;
import com.example.live_video.vo.CourseVo;
import com.example.live_video.vo.StudentGradeVo;
import com.example.live_video.vo.StudentSectionGradeVo;
import com.example.live_video.vo.StudentSectionProgressVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import com.example.live_video.wrapper.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ResponseResult
@RestController
@UserLoginToken
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    CourseMapper courseMapper;

    @GetMapping("/isRelated")
    boolean getRelated(@RequestParam long courseId, String userName){
        Long userId = userService.getUserId(userName);
        Course course = courseService.getOneCourse(courseId);
        boolean flag =  studentMapper.getRelationship(userId, courseId);
        boolean flag1 = Objects.equals(course.getTeacherId(), userId);
        return flag || flag1;
    }

    @GetMapping("/{courseId}")
    CourseVo getCourse(@PathVariable int courseId) {
        Course course = courseService.getOneCourse((long) courseId);
        course.setTeacherName(userService.getById(course.getTeacherId()).getUserName());
        return CourseVo.parse(course);
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
        System.out.println(userService.getUserType(userName));
        if (userService.getUserType(userName) == UserType.Student) {
            return CourseVo.parse(studentService.getEnrolledCourseList(userName));
        }
        if (userService.getUserType(userName) == UserType.Teacher) {
            return CourseVo.parse(courseService.getApprovedCourseListOfTeacher(o, page, userName));
        }
        return null;
    }

    @PostMapping ("/modify")
    public Boolean modifyCourseOfTeacher(@RequestBody CourseDto courseDto) throws MyException {
        if(!courseMapper.existCourse(courseDto.getTeacherName(), courseDto.getCourseName()))
            throw new MyException("Course doesn't exist");
        return courseService.updateCourse(courseDto.convertToCourse(CourseStatus.REVIEWING));
    }

    @PostMapping ("/insert")
    public Boolean insertCourseOfTeacher(@RequestBody CourseDto courseDto) throws SQLCoursenameConflictException {
        return courseService.createCourse(courseDto.convertToCourse(CourseStatus.REVIEWING));
    }

    @PostMapping("/del/{courseId}")
    public Boolean delCourseOfTeacher(@PathVariable String courseId) {
        return courseService.removeCourse(Long.valueOf(courseId));
    }

    @GetMapping("/waiting")
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
        System.out.println("JoinForm: "+joinForm);
        return studentService.enrollCourse(courseId, studentName);
    }

    @PostMapping("exit")
    public Boolean exit(@RequestBody JoinForm joinForm) throws Exception {
        Long courseId = joinForm.getCourseId();
        String studentName = joinForm.getStudentName();
        System.out.println(joinForm);
        return studentService.exitCourse(courseId, studentName);
    }

    @GetMapping("/{courseId}/grades")
    public List<StudentGradeVo> getStudentGrades(@PathVariable String courseId){
        return studentService.getStudentGrades(Long.parseLong(courseId));
    }

    @GetMapping("/student/section/grades")
    public List<StudentSectionProgressVo> getStudentSectionGrades(@RequestParam String studentName, @RequestParam Long courseId){
        long studentId = userService.getUserId(studentName);
        return studentService.getStudentSectionsProgress(studentId, courseId);
    }

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response, @RequestParam String courseId) {
        List<StudentGradeVo> activityList = studentService.getStudentGrades(Long.parseLong(courseId));
        FileWithExcelUtil.exportExcel(activityList, "成绩表", "sheet页名称", StudentGradeVo.class, "成绩表.xls", response);
    }
}
