package com.example.live_video.controller;

import com.example.live_video.dto.DeleteNoticeForm;
import com.example.live_video.dto.NoticeForm;
import com.example.live_video.entity.Course;
import com.example.live_video.entity.Notice;
import com.example.live_video.exception.MyException;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.NoticeService;
import com.example.live_video.vo.NoticeVo;
import com.example.live_video.wrapper.PassToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@PassToken
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @Autowired
    CourseService courseService;

    @GetMapping("/all")
    public List<NoticeVo> getNoticesOfCourse(@RequestParam String courseId) {
        return NoticeVo.parseNoticeList(noticeService.getNoticeListOfCourse(Long.valueOf(courseId)));
    }

    @GetMapping("/user/all")
    public List<NoticeVo> getNoticesOfUser(@RequestParam String userName) {
        List<Course> courseList = courseService.getApprovedCourseListOfTeacher(100, 1, userName);
        List<Notice> noticeList = new ArrayList<>();
        courseList.forEach(course -> noticeList.addAll(noticeService.getNoticeListOfCourse(course.getId())));
        return NoticeVo.parseNoticeList(noticeList);
    }

    @PostMapping("/create")
    public boolean createNotice(@RequestBody NoticeForm noticeForm) throws MyException {
        String title = noticeForm.getTitle();
        long courseId = noticeForm.getCourseId();
        String context = noticeForm.getContext();
        boolean sendMail = noticeForm.isSendMail();
        Notice notice = new Notice(title, courseId, context);
        noticeService.createNotice(notice, sendMail);
        return true;
    }

    @PostMapping("/del")
    public boolean deleteNotice(@RequestBody DeleteNoticeForm deleteNoticeForm){
        long noticeId = deleteNoticeForm.getNoticeId();
        noticeService.deleteNotice(noticeId);
        return true;
    }
}
