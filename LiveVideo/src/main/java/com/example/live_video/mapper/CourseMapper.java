package com.example.live_video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.live_video.entity.Course;
import org.apache.ibatis.annotations.Select;

public interface CourseMapper extends BaseMapper<Course> {

    /**
     * if the course exists in the teacher's courseList, it will return false
     * @param teacherName the name of the teacher who creates the course
     * @param courseName the name of the course
     * @return true if exists
     */
    @Select({"SELECT EXISTS(\n" +
            "               SELECT 1\n" +
            "               FROM user\n" +
            "                        JOIN course ON user.id = course.user_id\n" +
            "               where username = #{teacherName}\n" +
            "                 AND coursename = #{courseName}\n" +
            "                 AND user.is_delete = 0\n" +
            "                 AND course.is_delete = 0)"})
    public Boolean existCourse(String teacherName, String courseName);

}
