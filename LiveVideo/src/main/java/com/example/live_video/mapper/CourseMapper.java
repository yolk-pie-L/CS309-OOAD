package com.example.live_video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.live_video.entity.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * if the course exists in the teacher's courseList, it will return false
     * @param teacherName the name of the teacher who creates the course
     * @param courseName the name of the course
     * @return true if exists
     */
    @Select({"SELECT EXISTS(" +
            "               SELECT 1" +
            "               FROM user\n" +
            "                        JOIN course ON user.id = course.user_id\n" +
            "               where username = #{teacherName}\n" +
            "                 AND coursename = #{courseName}\n" +
            "                 AND user.is_delete = 0\n" +
            "                 AND course.is_delete = 0)"})
    public Boolean existCourse(String teacherName, String courseName);

    @Update({"UPDATE course\n" +
            "         LEFT JOIN user ON course.user_id = user.id\n" +
            "SET course.is_delete = course.id\n" +
            "WHERE course.coursename = #{courseName}\n" +
            "  AND user.username = #{teacherName}\n" +
            "  AND user.is_delete = 0\n" +
            "  AND course.is_delete = 0;"})
    public Boolean removeCourse(String teacherName, String courseName);

    @Select("SELECT * FROM course WHERE status = 'REVIEWING' AND is_delete = 0 LIMIT #{limit} OFFSET #{offset}")
    public List<Course> getReviewingCourses(int limit, int offset);

    @Select("SELECT * FROM course WHERE status = 'APPROVED' AND is_delete = 0 LIMIT #{limit} OFFSET #{offset}")
    public List<Course> getApprovedCourses(int limit, int offset);


    @Select("SELECT course.*\n" +
            "FROM course\n" +
            "         JOIN user ON course.user_id = user.id\n" +
            "WHERE username = #{teacherName}\n" +
            "  AND status = 'APPROVED'\n" +
            "  AND user.is_delete = 0\n" +
            "  AND course.is_delete = 0\n" +
            "LIMIT #{limit} OFFSET #{offset}")
    public List<Course> getApprovedCoursesOfTeacher(int limit, int offset, String teacherName);

    @Select("SELECT course.*\n" +
            "FROM course\n" +
            "         JOIN user ON course.user_id = user.id\n" +
            "WHERE username = #{teacherName}\n" +
            "  AND status = 'REVIEWING'\n" +
            "  AND user.is_delete = 0\n" +
            "  AND course.is_delete = 0\n" +
            "LIMIT #{limit} OFFSET #{offset}")
    public List<Course> getReviewingCoursesOfTeacher(int limit, int offset, String teacherName);

    @Select("SELECT course.*\n" +
            "FROM course\n" +
            "         JOIN user ON course.user_id = user.id\n" +
            "WHERE username = #{teacherName}\n" +
            "  AND status = 'FAILED'\n" +
            "  AND user.is_delete = 0\n" +
            "  AND course.is_delete = 0\n" +
            "LIMIT #{limit} OFFSET #{offset}")
    public List<Course> getFailedCoursesOfTeacher(int limit, int offset, String teacherName);
}
