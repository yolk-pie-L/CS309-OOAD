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
        Long courseId = courseMapper.getCourseId(notice.getTeacherName(), notice.getCourseName());
        notice.setCourseId(courseId);
        return noticeMapper.insert(notice) == 1;
    }

    @Override
    public List<Notice> getNoticeListOfCourse(String teacherName, String courseName) {
        Long courseId = courseMapper.getCourseId(teacherName, courseName);
        return noticeMapper.getNoticeListOfCourse(courseId);
    }

    @Override
    @Transactional
    public boolean deleteNotice(String teacherName, String courseName, Timestamp createTime) {
        Long courseId = courseMapper.getCourseId(teacherName, courseName);
        QueryWrapper<Notice> noticeQueryWrapper = new QueryWrapper<>();
        noticeQueryWrapper.eq("course_id", courseId);
        noticeQueryWrapper.eq("create_time", createTime);
        return noticeMapper.delete(noticeQueryWrapper) == 1;
    }
}
