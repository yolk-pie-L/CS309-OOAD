package com.example.live_video.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.live_video.entity.Assignment;
import com.example.live_video.exception.SQLAssignNameConflictException;
import com.example.live_video.service.AssignmentService;
import com.example.live_video.service.StudentService;
import com.example.live_video.vo.AssignmentVo;
import com.example.live_video.vo.QuizProblemVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @GetMapping("/all")
    public List<AssignmentVo> queryAssignmentByCourse(@RequestParam("userName") String userName,
                                                      @RequestParam("courseId") long courseId) {
        return queryAssignmentAndQuizByCourse(userName, courseId, assignmentService, studentService, true);
    }

    protected static List<AssignmentVo> queryAssignmentAndQuizByCourse(String userName, long courseId,
                                                                       AssignmentService assignmentService,
                                                                       StudentService studentService,
                                                                       boolean isAssignment) {
        List<Assignment> assignmentList = assignmentService.getAssignmentsOfCourse(courseId);
        List<AssignmentVo> assignmentVoList = new ArrayList<>();
        for (Assignment a : assignmentList) {
            if (a.getIsAssignment() == isAssignment) continue;
            AssignmentVo b = AssignmentVo.parseInfo(a);
            long assignId = assignmentService.getAssignmentId(courseId, a.getAssignmentName());
            b.setStatus(getStatus(a, assignId, userName, studentService));
            assignmentVoList.add(b);
        }
        return assignmentVoList;
    }

    @GetMapping("/one")
    public AssignmentVo queryAssignmentById(@RequestParam("userId") String userName,
                                            @RequestParam("assignmentId") long assignId) {
        return queryAssignmentAndQuizById(userName, assignId, assignmentService, studentService, true);
    }

    protected static AssignmentVo queryAssignmentAndQuizById(String userName, long assignId,
                                                             AssignmentService assignmentService,
                                                             StudentService studentService,
                                                             boolean isAssignment) {
        Assignment a = assignmentService.getOneAssignment(assignId);
        AssignmentVo b = AssignmentVo.parse(a);
        // set some elements
        b.setStatus(getStatus(a, assignId, userName, studentService));
        b.setScore(studentService.getStudentAssignGrade(userName, assignId));
        b.setAttached(assignmentService.getAssignmentUrlList(assignId));
        if (isAssignment)
            b.setAnswer(studentService.getStudentAssignmentUrlList(userName, assignId));
        else try {
            String quizUrl = a.getAssignUrls().get(0);
            Path path = Paths.get(quizUrl);
            List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            String jsonStr = String.join("", allLines);
            List<QuizProblemVo> list = JSONObject.parseArray(jsonStr, QuizProblemVo.class);  // String(json form) to Object List
            b.setProblems(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // return to front end
        return b;
    }

    @PostMapping("/submit")
    public boolean submitAssignment(@RequestParam("userId") String userName,
                                    @RequestParam("assignmentId") long assignId,
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
        if (!dest.getParentFile().exists())
            System.out.println("CREATE A NEW DIRECTORY: " + dest.getParentFile().mkdirs());
        f.transferTo(dest);
        System.out.println("SAVE TO: " + url);
        return url;
    }

    @PostMapping("/create")
    public boolean createAssignment(@RequestParam AssignmentVo assignmentVo) {
        try {
            Assignment a = AssignmentVo.voToAssign(assignmentVo);
            return assignmentService.createAssignment(a);
        } catch (SQLAssignNameConflictException e) {
            throw new RuntimeException(e);
        }
    }

    // util methods:
    @NotNull
    protected static String getStatus(Assignment a, long assignId, String userName, StudentService studentService) {
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
