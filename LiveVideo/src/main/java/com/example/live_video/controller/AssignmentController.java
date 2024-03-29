package com.example.live_video.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.live_video.dto.AssignForm;
import com.example.live_video.dto.ModifyStuAssignForm;
import com.example.live_video.entity.Assignment;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.service.AssignmentService;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.StudentService;
import com.example.live_video.service.UserService;
import com.example.live_video.util.TokenUtils;
import com.example.live_video.vo.AssignmentVo;
import com.example.live_video.vo.QuizProblemVo;
import com.example.live_video.vo.StringVo;
import com.example.live_video.vo.StuAssignVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import com.example.live_video.wrapper.UserLoginToken;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import static com.example.live_video.controller.PictureController.getFileNameNew;

@ResponseResult
@RestController
@PassToken
//@UserLoginToken
@RequestMapping("/api/assignment")
public class AssignmentController {

    @Value("${src.assign-path}")
    private String assignPath;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public List<AssignmentVo> queryAssignmentByCourse(@RequestHeader("token") String token,
                                                      @RequestParam("courseId") long courseId) throws Exception {
        String userName = token2userName(token);
        return queryAssignmentAndQuizByCourse(userName, courseId, assignmentService, studentService, userService, true);
    }

    @PostMapping("/modify")
    public void checkAssignment(@RequestBody ModifyStuAssignForm modifyStuAssignForm){
        studentService.setStudentAssignGrade(modifyStuAssignForm.getStudentName(), Long.parseLong(modifyStuAssignForm.getId()),
                Integer.parseInt(modifyStuAssignForm.getGrade()));
    }

    @GetMapping("/stuassign")
    public List<StuAssignVo> getAllStuAssign(@RequestParam String courseId){
        List<Assignment> assignmentList = assignmentService.getAssignmentsOfCourse(Long.parseLong(courseId));
        List<StuAssignVo> stuAssignVos = new ArrayList<>();
        List<User> users = studentService.getStudentListOfOneCourse(Long.parseLong(courseId));
        for(Assignment assignment: assignmentList){
            for(User user: users){
                StuAssignVo stuAssignVo = new StuAssignVo();
                stuAssignVo.setAssignId(assignment.getId());
                stuAssignVo.setAssignmentName(assignment.getAssignmentName());
                stuAssignVo.setTotalGrade(assignment.getTotalGrade());
                stuAssignVo.setStudentName(user.getUserName());
                stuAssignVo.setAnswers(studentService.getStudentAssignmentUrlList(user.getUserName(), assignment.getId()));
                stuAssignVos.add(stuAssignVo);
            }
        }
        System.out.println("stuAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(stuAssignVos);
        return stuAssignVos;
    }

    protected static List<AssignmentVo> queryAssignmentAndQuizByCourse(String userName, long courseId,
                                                                       AssignmentService assignmentService,
                                                                       StudentService studentService,
                                                                       UserService userService,
                                                                       boolean isAssignment) {
        List<Assignment> assignmentList = assignmentService.getAssignmentsOfCourse(courseId);
        System.out.println(assignmentList);
        System.out.println("userName: " + userName);
        List<AssignmentVo> assignmentVoList = new ArrayList<>();
        User user = userService.getUser(userName);
        for (Assignment a : assignmentList) {
            if (a.getIsAssignment() != isAssignment) continue;
            AssignmentVo b = AssignmentVo.parseInfo(a);
            long assignId = assignmentService.getAssignmentId(courseId, a.getAssignmentName());
            if (user.getUserType().equals(UserType.Student))
                b.setStatus(getStatus(a, assignId, userName, studentService));
            else
                b.setStatus("Ignored");
            assignmentVoList.add(b);
        }
        return assignmentVoList;
    }

    @GetMapping("/one")
    public AssignmentVo queryAssignmentById(@RequestHeader("token") String token,
                                            @RequestParam("assignmentId") long assignId) throws Exception {
        String userName = token2userName(token);
        return queryAssignmentAndQuizById(userName, assignId, assignmentService, studentService, userService,true);
    }

    protected static AssignmentVo queryAssignmentAndQuizById(String userName, long assignId,
                                                             AssignmentService assignmentService,
                                                             StudentService studentService,
                                                             UserService userService,
                                                             boolean isAssignment) {
        Assignment a = assignmentService.getOneAssignment(assignId);
        AssignmentVo b = AssignmentVo.parse(a);
        // set some elements
        User user = userService.getUser(userName);
        if (user.getUserType().equals(UserType.Student)) {
            b.setStatus(getStatus(a, assignId, userName, studentService));
            b.setScore(studentService.getStudentAssignGrade(userName, assignId));
        } else {
            b.setStatus("Ignored");
            b.setScore(0);
        }
        System.out.println("UserName, Assign"+userName+assignId);
        if (isAssignment)
            b.setAnswer(studentService.getStudentAssignmentUrlList(userName, assignId));
        else try {
            if (a.getAssignUrls().isEmpty()) {
                System.out.println("没有url");
                return b;
            }
            String quizUrl = "src/main/resources/static/files/" + a.getAssignUrls().get(0);
            Path path = Paths.get(quizUrl);
            List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            String jsonStr = String.join("", allLines);
            QuizProblemVo qpv = JSONObject.parseObject(jsonStr, QuizProblemVo.class);  // String(json form) to Object List
            b.setProblemSet(qpv);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // return to front end
        System.err.println("b:\n" + b);
        return b;
    }

    @PostMapping("/upload")
    public StringVo uploadFile(@RequestParam MultipartFile f) throws IOException {
        String filePath = assignPath;
        String fileName = f.getOriginalFilename();
        assert fileName != null;
        String[] strs = fileName.split("\\.");
        fileName = "assignFile_" + getFileNameNew() + "." + strs[strs.length - 1];
        String url = filePath + fileName;
        File dest = new File(url);
        if (!dest.getParentFile().exists())
            System.out.println("CREATE A NEW DIRECTORY: " + dest.getParentFile().mkdirs());
        f.transferTo(dest.getCanonicalFile());
        System.out.println("SAVE TO: " + url);
        return new StringVo(fileName);
    }

    @PostMapping("/submit")
    public boolean submitAssignment(@RequestHeader("token") String token,
                                    @RequestParam("assignmentId") long assignId,
                                    @RequestParam("answerFile") List<String> answerFile,
                                    @RequestParam("status") String status) throws Exception {
        String userName = token2userName(token);
        return studentService.submitAssignment(userName, assignId, answerFile);
    }


    @PostMapping("/create")
    public boolean createAssignment(@RequestBody AssignForm assignForm) throws Exception{
        Assignment a = new Assignment(assignForm.getAssignmentName(), assignForm.getCourseId(),
                new Timestamp(assignForm.getDeadline().getTime()), assignForm.getTotalGrade(),
                assignForm.getAdditionalResources(), true, assignForm.getDescription());
        return assignmentService.createAssignment(a);
    }

    // util methods:
    protected static String token2userName(String token) throws Exception {
        return TokenUtils.getUserName(token);
    }

    @NotNull
    protected static String getStatus(Assignment a, long assignId, String userName, StudentService studentService) {
        Timestamp createTime = a.getCreateTime();
        Timestamp ddl = a.getDeadline();
        Timestamp now = new Timestamp(new Date().getTime());
        int score = studentService.getStudentAssignGrade(userName, assignId);
        List<String> urls = studentService.getStudentAssignmentUrlList(userName, assignId);
        System.out.println("uuuuuuuuurls" + urls);
        if (urls.size() == 0) {
            if (createTime.after(now)) return "Not Started";
            else if (ddl.after(now)) return "Not Submitted";
            else return "Late";
        } else {
            if (score == 0) return "Submitted";
            else return "Return";
        }
    }
}
