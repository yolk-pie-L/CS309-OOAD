package com.example.live_video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.live_video.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    @Select("SELECT t.*\n" +
            "FROM livevideo.notice t\n" +
            "WHERE course_id = #{courseId}\n" +
            "ORDER BY create_time DESC")
    public List<Notice> getNoticeListOfCourse(Long courseId);
}
