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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.example.live_video.constance.FileConstance.FILE_PATH;
import static com.example.live_video.controller.AssignmentController.*;
import static com.example.live_video.controller.PictureController.getFileNameNew;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/quiz")
public class QuizController {
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private StudentService studentService;

    @PostMapping("/createQuizJson")
    public String createQuizJson(@RequestBody QuizProblemVo problems) {
        try {
            String jsonStr = JSONObject.toJSONString(problems);  // Object List to String(json form)
            String filePath = FILE_PATH + "\\quizFiles\\";
            String fileName = "quizJson_" + getFileNameNew() + ".json";
            String quizUrl = filePath + fileName;
            Files.write(Paths.get(quizUrl), jsonStr.getBytes(StandardCharsets.UTF_8));
            System.out.println("SAVE TO: " + quizUrl);
            return quizUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @PostMapping("/createQuiz")
    public boolean createQuiz(@RequestParam AssignmentVo quizVo) {
        Assignment a = AssignmentVo.voToAssign(quizVo);
        a.setIsAssignment(false);
        try {
            return assignmentService.createAssignment(a);
        } catch (SQLAssignNameConflictException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/all")
    public List<AssignmentVo> queryQuizByCourse(@RequestHeader("token") String token,
                                                @RequestParam("courseId") long courseId) throws Exception {
        String userName = token2userName(token);
        return queryAssignmentAndQuizByCourse(userName, courseId, assignmentService, studentService, false);
    }

    @GetMapping("/one")
    public AssignmentVo queryQuizById(@RequestHeader("token") String token,
                                      @RequestParam("assignmentId") long quizId) throws Exception {
        String userName = token2userName(token);
        return queryAssignmentAndQuizById(userName, quizId, assignmentService, studentService, false);
    }

    @PostMapping("/submit")
    public Integer submitQuiz(@RequestHeader("token") String token,
                              @RequestParam("assignmentId") long quizId,
                              @RequestParam("answer") List<String> choice) throws Exception {
        try {
            String userName = token2userName(token);
            String jsonStr = JSONObject.toJSONString(choice);  // Object List to String(json form)
            String filePath = FILE_PATH + "\\quizAnswerFiles\\";
            String fileName = "quizAnswerJson_" + getFileNameNew() + ".json";
            String quizUrl = filePath + fileName;
            Files.write(Paths.get(quizUrl), jsonStr.getBytes(StandardCharsets.UTF_8));
            System.out.println("SAVE TO: " + quizUrl);
            return getScore(userName, quizId);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // util
    protected Integer getScore(@RequestHeader("token") String token,
                               @RequestParam("assignmentId") long quizId) throws Exception {
        try {
            String userName = token2userName(token);
            String quizAnswerUrl = studentService.getStudentAssignmentUrlList(userName, quizId).get(0);
            Path path = Paths.get(quizAnswerUrl);
            List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            String jsonStr = String.join("", allLines);
            List<String> stuAnswer = JSONObject.parseArray(jsonStr, String.class);

            String quizUrl = assignmentService.getOneAssignment(quizId).getAssignUrls().get(0);
            path = Paths.get(quizUrl);
            allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            jsonStr = String.join("", allLines);
            QuizProblemVo qpv = JSONObject.parseObject(jsonStr, QuizProblemVo.class);

            int len = stuAnswer.size();
            int cnt = 0;
            if (len != qpv.getProblems().size()) {
                System.err.println("ERROR detect When calculating the score of the quiz. Lengths are not same!");
                return -1;
            }
            for (int i = 0; i < len; i++)
                if (stuAnswer.get(i).equalsIgnoreCase(qpv.getProblems().get(i).getAnswer())) cnt++;
            return 100 * cnt / len;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

