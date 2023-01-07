package com.example.live_video.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.live_video.entity.AdminRight;
import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.entity.User;
import com.example.live_video.exception.MyException;
import com.example.live_video.exception.PermissionDeniedException;
import com.example.live_video.mapper.UserMapper;
import com.example.live_video.service.AdminService;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.UserService;
import com.example.live_video.util.TokenUtils;
import com.example.live_video.vo.CourseVo;
import com.example.live_video.vo.UserVo;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @Autowired
    UserMapper userMapper;


    @GetMapping("/waiting")
    public List<CourseVo> queryCourseOfAdministrator() {
        List<Course> list1 = courseService.getReviewingCourseList();
        List<CourseVo> list2 = new ArrayList<>();
        for (Course c : list1) {
            CourseVo cv = CourseVo.parse(c);
            cv.setTeacherName(userService.getById(c.getTeacherId()).getUserName());
            list2.add(cv);
        }
        return list2;
    }

    @GetMapping("/all")
    // "/all?userName={LI}&type={all}"
    public List<UserVo> queryUserLikeUserName(@RequestParam("userName") String userName,
                                              @RequestParam("type") String type) {
        // type: "student", "teacher", or "all"
        List<UserVo> userVoList = UserVo.parse(adminService.getUserList(userName));
        if (type.equalsIgnoreCase("all")) return userVoList;
        userVoList.removeIf(u -> !u.getUserType().equalsIgnoreCase(type));
        return userVoList;
    }

    @PostMapping("privilege")
    public boolean updatePrivilege(@RequestHeader("token") String token, @RequestParam("userName") String userName)
            throws Exception {
        String name = TokenUtils.getUserName(token);
        if(name.equals(userName))
            throw new MyException("不能修改本人的管理员权限");
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", userName);
        User newAdmin = userMapper.selectOne(userQueryWrapper);
        if(newAdmin.getAdminRight().equals(AdminRight.SuperAdmin)){ // 超级管理员
            throw new PermissionDeniedException();
        }
        if(newAdmin.getAdminRight().equals(AdminRight.Admin)){ // 普通管理员变成非管理员
            newAdmin.setAdminRight(AdminRight.NonAdmin);
        }else{
            newAdmin.setAdminRight(AdminRight.Admin); // 非管理员变成管理员
        }
        return userService.updateUser(newAdmin);
    }

    @PostMapping("/status")
    public boolean updateCourseStatusByAdministrator(@RequestParam("courseId") String courseId,
                                                     @RequestParam("approved") String approved) {
        // note: 输入这里用的是id，跟json写得不一样；并且返回了bool
        boolean ok = approved.equalsIgnoreCase("yes") ||
                approved.equalsIgnoreCase("true") ||
                approved.equalsIgnoreCase("1");
        Course course = courseService.getOneCourse(Long.parseLong(courseId));
        course.setTeacherName(userService.getById(course.getTeacherId()).getUserName());
        course.setStatus(ok ? CourseStatus.APPROVED : CourseStatus.FAILED);
        return courseService.updateCourse(course);
    }


}
