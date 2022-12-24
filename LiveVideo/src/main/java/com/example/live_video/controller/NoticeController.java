package com.example.live_video.controller;

import com.example.live_video.entity.Notice;
import com.example.live_video.exception.MyException;
import com.example.live_video.service.NoticeService;
import com.example.live_video.vo.NoticeVo;
import com.example.live_video.wrapper.PassToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PassToken
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @GetMapping("/all")
    public List<NoticeVo> getNoticesOfCourse(@RequestParam long courseId) {
        return NoticeVo.parseNoticeList(noticeService.getNoticeListOfCourse(courseId));
    }

    @PostMapping("/create")
    public boolean createNotice(@RequestParam long courseId, @RequestParam String title, @RequestParam String context,
                                @RequestParam boolean sendMail) throws MyException {
        Notice notice = new Notice(title, courseId, context);
        noticeService.createNotice(notice, sendMail);
        return true;
    }

    @PostMapping("/del")
    public boolean deleteNotice(@RequestParam long noticeId){
        noticeService.deleteNotice(noticeId);
        return true;
    }
}
