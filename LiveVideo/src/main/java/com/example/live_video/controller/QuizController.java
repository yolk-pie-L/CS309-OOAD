package com.example.live_video.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.live_video.entity.Assignment;
import com.example.live_video.exception.SQLAssignNameConflictException;
import com.example.live_video.service.AssignmentService;
import com.example.live_video.service.CourseService;
import com.example.live_video.vo.AssignmentVo;
import com.example.live_video.vo.QuizProblemVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.example.live_video.controller.PictureController.getFileNameNew;


@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/quiz")
public class QuizController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/createQuizJson")
    public String createQuizJson(@RequestParam("problems") List<QuizProblemVo> problems) {
        try {
            // List<QuizProblemVo> list = JSONObject.parseArray(data, T);  // String(json form) to Object List
            String jsonStr = JSONObject.toJSONString(problems);  // Object List to String(json form)
            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files\\quizFiles\\";
            String fileName = "quizJson_" + getFileNameNew() + ".json";
            String quizUrl = filePath + fileName;
            Files.write(Paths.get(quizUrl), jsonStr.getBytes(StandardCharsets.UTF_8));
            System.out.println("SAVE TO: " + quizUrl);
            return quizUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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
    public List<AssignmentVo> queryQuizByCourse(@RequestParam("courseId") Long courseId) {
//        assignmentService



        return null;
    }


}










