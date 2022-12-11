package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.Notice;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.NoticeMapper;
import com.example.live_video.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    CourseMapper courseMapper;

    @Override
    public boolean createNotice(Notice notice) {
        return noticeMapper.insert(notice) == 1;
    }

    @Override
    public List<Notice> getNoticeListOfCourse(Long courseId) {
        return noticeMapper.getNoticeListOfCourse(courseId);
    }

    @Override
    @Transactional
    public boolean deleteNotice(Long noticeId) {
        return noticeMapper.deleteById(noticeId) == 1;
    }
}
