package com.example.live_video.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {

    @Insert("INSERT INTO stu_course(user_id, course_id) " +
            "VALUES (#{userId}, #{courseId})")
    public void enrollCourse(Long userId, Long courseId);

}
