package com.example.live_video.controller;

import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.entity.User;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.UserService;
import com.example.live_video.vo.CourseVo;
import com.example.live_video.vo.UserVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ResponseResult
@RestController
@PassToken
public class AdminController {
    @Autowired
    private CourseService courseService;
    //    @Autowired
//    private AdminService adminService;
    @Autowired
    private UserService userService;


    @GetMapping("/api/course/waiting")
    public List<CourseVo> queryCourseOfAdministrator() {
        return CourseVo.parse(courseService.getAllCourse());
    }

    @GetMapping("/api/user/all?userName={userName}&type={type}")
    public List<UserVo> queryUserLikeUserName(@PathVariable String userName,
                                              @PathVariable String type) {
        // type: "student", "teacher", or "all"
        return UserVo.parse(userService.getUserLikeUserName(userName, type));
    }

    @PostMapping("/api/privilege")
    public Boolean updatePrivilege(@RequestParam String userName) {
        User updateUser = new User();
        updateUser.setUserName(userName);
        // fixme
//        if (userService.getUser(userName).getUserType() == UserType.Administrator)
//            updateUser.setUserType("non Admin");
//        else updateUser.setUserType("Admin");
        return userService.updateUser(updateUser);
    }

    @GetMapping("/api/course/admin")
    public Boolean updateCourseStatusByAdministrator(@RequestParam String courseId,
                                                  @RequestParam String approved) {
        // note: 输入这里用的是id，跟json写得不一样；并且返回了bool
        Boolean ok = approved.equalsIgnoreCase("yes") ||
                approved.equalsIgnoreCase("true") ||
                approved.equalsIgnoreCase("1");
        Course course = courseService.getOneCourse(Long.parseLong(courseId));
        course.setStatus(ok ? CourseStatus.APPROVED : CourseStatus.FAILED);
        return courseService.updateCourse(course);
    }


}
