package com.example.live_video.mapper;

import com.example.live_video.entity.Course;
import org.apache.ibatis.annotations.*;

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
    public List<Course> getEnrolledCourseList(Long userId);

    @Update("UPDATE stu_assign SET grade = #{grade} WHERE user_id = #{studentId} AND assign_id = #{assignId}")
    public void setStudentAssignGrade(Long studentId, Long assignId, int grade);

    @Insert("INSERT INTO stu_assign(user_id, assign_id, assign_url) VALUES (#{studentId}, #{assignId}, #{assign_url})")
    public void submitAssignment(Long studentId, Long assignId, String assign_url);

    @Select("SELECT assign_url FROM stu_assign WHERE user_id = #{studentId} AND assign_id = #{assignId}")
    public List<String> getStudentSubmittedAssignUrlList(Long studentId, Long assignId);

    @Delete("DELETE FROM stu_assign WHERE user_id = #{studentId} AND assign_id = #{assignId}")
    public void deleteStudentAssignment(Long studentId, Long assignId);
}
