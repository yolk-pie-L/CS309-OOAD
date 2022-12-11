package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.Notice;

import java.sql.Timestamp;
import java.util.List;

public interface NoticeService extends IService<Notice> {

    public boolean createNotice(Notice notice);

    public List<Notice> getNoticeListOfCourse(Long courseId);

    public boolean deleteNotice(Long noticeId);
}
