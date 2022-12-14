package com.example.live_video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.live_video.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * if the course exists in the teacher's courseList, it will return false
     *
     * @param teacherName the name of the teacher who creates the course
     * @param courseName  the name of the course
     * @return true if exists
     */
    @Select({"SELECT EXISTS(" +
            "               SELECT 1" +
            "               FROM user\n" +
            "                        JOIN course ON user.id = course.teacher_id\n" +
            "               where username = #{teacherName}\n" +
            "                 AND course_name = #{courseName}\n" +
            "                 AND user.is_delete = 0\n" +
            "                 AND course.is_delete = 0)"})
    public Boolean existCourse(String teacherName, String courseName);

    @Update({"UPDATE course\n" +
            "         LEFT JOIN user ON course.teacher_id = user.id\n" +
            "SET course.is_delete = course.id\n" +
            "WHERE course.course_name = #{courseName}\n" +
            "  AND user.username = #{teacherName}\n" +
            "  AND user.is_delete = 0\n" +
            "  AND course.is_delete = 0;"})
    public Boolean removeCourse(String teacherName, String courseName);

    @Select({"SELECT course.id\n" +
            "FROM course\n" +
            "         JOIN user ON course.teacher_id = user.id\n" +
            "WHERE username = #{teacherName}\n" +
            "  AND course_name = #{courseName}\n" +
            "  AND course.is_delete = 0\n" +
            "  AND user.is_delete = 0"})
    public Long getCourseId(String teacherName, String courseName);

    @Select({"SELECT course.charge\n" +
            "FROM course\n" +
            "         JOIN user ON course.teacher_id = user.id\n" +
            "WHERE course.id = #{courseId}"})
    public Long getCourseCharge(Long courseId);

    @Select({"SELECT course.*\n" +
            "FROM course\n" +
            "         JOIN user ON course.teacher_id = user.id\n" +
            "WHERE username = #{teacherName}\n" +
            "  AND course_name = #{courseName}\n" +
            "  AND course.is_delete = 0\n" +
            "  AND user.is_delete = 0"})
    public Course getOneCourse(String teacherName, String courseName);

    @Select({"SELECT course.*\n" +
            "FROM course\n" +
            "         JOIN user ON course.teacher_id = user.id\n" +
            "WHERE username = #{teacherName}\n" +
            "  AND course_name = #{courseName}\n" +
            "  AND status = 'APPROVED'\n" +
            "  AND course.is_delete = 0\n" +
            "  AND user.is_delete = 0"})
    public Course getOneApprovedCourse(String teacherName, String courseName);

    @Select("SELECT * FROM course WHERE status = 'REVIEWING' AND is_delete = 0 LIMIT #{limit} OFFSET #{offset}")
    public List<Course> getReviewingCourseList(int limit, int offset);

    @Select("SELECT * FROM course WHERE status = 'APPROVED' AND is_delete = 0 LIMIT #{limit} OFFSET #{offset}")
    public List<Course> getApprovedCourseList(int limit, int offset);


    @Select("SELECT course.*\n" +
            "FROM course\n" +
            "         JOIN user ON course.teacher_id = user.id\n" +
            "WHERE username = #{teacherName}\n" +
            "  AND status = 'APPROVED'\n" +
            "  AND user.is_delete = 0\n" +
            "  AND course.is_delete = 0\n" +
            "LIMIT #{limit} OFFSET #{offset}")
    public List<Course> getApprovedCourseListOfTeacher(int limit, int offset, String teacherName);

    @Select("SELECT course.*\n" +
            "FROM course\n" +
            "         JOIN user ON course.teacher_id = user.id\n" +
            "WHERE username = #{teacherName}\n" +
            "  AND status = 'REVIEWING'\n" +
            "  AND user.is_delete = 0\n" +
            "  AND course.is_delete = 0\n" +
            "LIMIT #{limit} OFFSET #{offset}")
    public List<Course> getReviewingCourseListOfTeacher(int limit, int offset, String teacherName);

    @Select("SELECT course.*\n" +
            "FROM course\n" +
            "         JOIN user ON course.teacher_id = user.id\n" +
            "WHERE username = #{teacherName}\n" +
            "  AND status = 'FAILED'\n" +
            "  AND user.is_delete = 0\n" +
            "  AND course.is_delete = 0\n" +
            "LIMIT #{limit} OFFSET #{offset}")
    public List<Course> getFailedCourseListOfTeacher(int limit, int offset, String teacherName);

    @Select("SELECT * FROM course\n" +
            "WHERE course_name = #{courseName} \n" +
            "  AND status = 'APPROVED'\n" +
            "  AND course.is_delete = 0\n" +
            "LIMIT #{limit} OFFSET #{offset}")
    public List<Course> getApprovedCourseListByCourseName(int limit, int offset, String courseName);
}
