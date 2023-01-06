package com.example.live_video.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.live_video.dto.QuizSubmitDto;
import com.example.live_video.entity.Assignment;
import com.example.live_video.service.AssignmentService;
import com.example.live_video.service.StudentService;
import com.example.live_video.service.UserService;
import com.example.live_video.vo.AssignmentVo;
import com.example.live_video.vo.QuizProblemVo;
import com.example.live_video.vo.StringVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.example.live_video.controller.AssignmentController.*;
import static com.example.live_video.controller.PictureController.getFileNameNew;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/quiz")
public class QuizController {
    @Value("${src.assign-path}")
    private String quizPath;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;

    @PostMapping("/createQuizJson")
    public StringVo createQuizJson(@RequestBody QuizProblemVo problemSet) throws IOException {
        System.err.println(problemSet);
        String jsonStr = JSONObject.toJSONString(problemSet);  // Object List to String(json form)
        String filePath = quizPath;
        String fileName = "quizJson_" + getFileNameNew() + ".json";
        String quizUrl = filePath + fileName;

        File file = new File(filePath);
        if (!file.exists() && !file.isDirectory()) file.mkdir();
        file = new File(quizUrl);
        if (!file.exists()) file.createNewFile();

        Files.write(Paths.get(quizUrl), jsonStr.getBytes(StandardCharsets.UTF_8));
        System.out.println("SAVE TO: " + quizUrl);
        return new StringVo(fileName);
    }

    @PostMapping("/createQuiz")
    public boolean createQuiz(@RequestBody AssignmentVo quizVo) throws Exception {
        System.err.println(quizVo.getAssignUrls());
        Assignment a = AssignmentVo.voToAssign(quizVo);
        a.setIsAssignment(false);
        a.setCourseId(quizVo.getCourseId());
        assert (a.getCourseId() != null);
        System.err.println(a.getAssignUrls());
        return assignmentService.createAssignment(a);
    }

    @GetMapping("/all")
    public List<AssignmentVo> queryQuizByCourse(@RequestHeader("token") String token,
                                                @RequestParam("courseId") long courseId) throws Exception {
        String userName = token2userName(token);
        return queryAssignmentAndQuizByCourse(userName, courseId, assignmentService, studentService, userService, false);
    }

    @GetMapping("/one")
    public AssignmentVo queryQuizById(@RequestHeader("token") String token,
                                      @RequestParam("assignmentId") long quizId) throws Exception {
        String userName = token2userName(token);
        AssignmentVo assignmentVo = queryAssignmentAndQuizById(userName, quizId, assignmentService, studentService, userService, false);
//        List<String> urls = assignmentVo.getAssignUrls();
//        urls.set(0, quizPath + urls.get(0));
//        System.out.println(urls.size());
//        assignmentVo.setAssignUrls(urls);
        return assignmentVo;
    }

    @PostMapping("/submit")
    public Integer submitQuiz(@RequestHeader("token") String token,
                              @RequestBody QuizSubmitDto quizSubmitDto) throws Exception {
        String userName = token2userName(token);
        System.err.println(userName);
        String jsonStr = JSONObject.toJSONString(quizSubmitDto.getChoice());  // Object List to String(json form)
        String filePath = quizPath;
        String fileName = "quizAnswerJson_" + getFileNameNew() + ".json";
        String quizUrl = filePath + fileName;
        Files.write(Paths.get(quizUrl), jsonStr.getBytes(StandardCharsets.UTF_8));
        System.out.println("SAVE TO: " + quizUrl);
        List<String> urls = new ArrayList<>();
        urls.add(quizUrl);
        studentService.submitAssignment(userName, quizSubmitDto.getQuizId(), urls);
        return getScore(userName, quizSubmitDto.getQuizId());
    }


    // util
    protected Integer getScore(String userName, long quizId) throws Exception {
        List<String> quizAnswerUrls = studentService.getStudentAssignmentUrlList(userName, quizId);
        if (quizAnswerUrls.isEmpty()) return -1;
        String quizAnswerUrl = quizAnswerUrls.get(0);
        Path path = Paths.get(quizAnswerUrl);
        List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        String jsonStr = String.join("", allLines);
        List<String> stuAnswer = JSONObject.parseArray(jsonStr, String.class);

        String quizUrl = quizPath + assignmentService.getOneAssignment(quizId).getAssignUrls().get(0);
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
        for (int i = 0; i < len; i++) {
            System.out.println(stuAnswer.get(i));
            System.out.println(qpv.getProblems().get(i).getAnswer());
            if (stuAnswer.get(i).equalsIgnoreCase(qpv.getProblems().get(i).getAnswer())
                    || stuAnswer.get(i).equalsIgnoreCase("selection" + qpv.getProblems().get(i).getAnswer())) cnt++;
        }
        return 100 * cnt / len;
    }
}

