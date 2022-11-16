package com.example.live_video.mapper;

import com.example.live_video.entity.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Insert("INSERT INTO stu_course(user_id, course_id) " +
            "VALUES (#{userId}, #{courseId})")
    public void enrollCourse(Long userId, Long courseId);

    @Delete("DELETE FROM stu_course WHERE user_id = #{userId} AND course_id = #{courseId}")
    public void exitCourse(Long userId, Long courseId);

    @Select("SELECT course.* FROM course JOIN stu_course ON course.id = stu_course.course_id " +
            "WHERE user_id = #{userId}")
    public List<Course> getEnrolledCourses(Long userId);

}
