package com.example.live_video.controller;

import com.example.live_video.entity.Assignment;
import com.example.live_video.service.AssignmentService;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.StudentService;
import com.example.live_video.service.UserService;
import com.example.live_video.vo.AssignmentVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/assignment")
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/all")
    public List<AssignmentVo> queryAssignmentByCourse(@RequestParam("userName") String userName,
                                                      @RequestParam("courseId") Long courseId) {
        List<Assignment> assignmentList = assignmentService.getAssignmentsOfCourse(courseId);
        List<AssignmentVo> assignmentVoList = new ArrayList<>();
        for (Assignment a : assignmentList) {
            AssignmentVo b = AssignmentVo.parseInfo(a);
            Long assignId = assignmentService.getAssignmentId(courseId, a.getAssignmentName());
            b.setStatus(getStatus(a, assignId, userName));
            assignmentVoList.add(b);
        }
        return assignmentVoList;
    }

    @GetMapping("/one")
    public AssignmentVo queryAssignmentById(@RequestParam("userId") String userName,
                                            @RequestParam("assignmentId") Long assignId) {
        Assignment a = assignmentService.getOneAssignment(assignId);
        AssignmentVo b = AssignmentVo.parse(a);
        b.setStatus(getStatus(a, assignId, userName));
        b.setScore(studentService.getStudentAssignGrade(userName, assignId));
        b.setAttached(assignmentService.getAssignmentUrlList(assignId));
        b.setAnswer(studentService.getStudentAssignmentUrlList(userName, assignId));
        return b;
    }

    @PostMapping("/submit")
    public boolean submitAssignment(@RequestParam("userId") String userName,
                                    @RequestParam("assignmentId") Long assignId,
                                    @RequestParam("answerFile") List<String> answerFile) {
        return studentService.submitAssignment(userName, assignId, answerFile);
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam MultipartFile f) throws IOException {
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files\\assignFiles\\";
        String fileName = f.getOriginalFilename();
        assert fileName != null;
        String[] strs = fileName.split("\\.");
        fileName = strs[strs.length - 2] + strs[strs.length - 1];
        String url = filePath + fileName;
        File dest = new File(url);
        if (!dest.getParentFile().exists()) dest.getParentFile().mkdirs();
        f.transferTo(dest);
        System.out.println("SAVE TO: " + url);
        return url;
    }

    @PostMapping("/create")
    public boolean createAssignment(@RequestParam AssignmentVo assignmentVo) {
        Assignment a = new Assignment();
        return false;
    }

    private String getStatus(Assignment a, Long assignId, String userName) {
        // Not Started / Not Submitted / Late / Submitted / Return
        Timestamp startTime = a.getStartTime();
        Timestamp ddl = a.getDeadline();
        Timestamp now = new Timestamp(new Date().getTime());
        int score = studentService.getStudentAssignGrade(userName, assignId);
        if (score == -1) {
            if (startTime.after(now)) return "Not Started";
            else if (ddl.after(now)) return "Not Submitted";
            else return "Late";
        } else {
            if (score == 0) return "Submitted";
            else return "Return";
        }
    }


}
