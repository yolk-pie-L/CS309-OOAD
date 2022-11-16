package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.Course;
import com.example.live_video.exception.SQLCoursenameConflictException;

import java.util.List;

public interface CourseService extends IService<Course> {

    /**
     * Teacher creates his course. After course creation, the course will be inserted in to the
     * database with status REVIEWING
     *
     * @param course the course to be created
     * @return true if the course is created successfully
     * @throws SQLCoursenameConflictException
     */
    public Object createCourse(Course course);


    /**
     * Update the course. Any field that is not empty in the course will be updated.
     * @param course course to be updated
     * @return true if the course is updated successfully
     * @throws
     */
    public boolean updateCourse(Course course);

    public boolean removeCourse(String teacherName, String courseName);

//    public Boolean getCourseIdByCourseNameTeacherName(String courseName, String teacherName);

    /**
     * Get the courses that has status REVIEWING.
     *
     * @return A list of course
     */
    public List<Course> getReviewingCourses();


    /**
     * Get the courses that has status APPROVED. 实现了分页
     * @param recordsPerPage 每一页的记录数量
     * @param pageNum 第几页（从1开始）
     * @return A list of course
     */
    public List<Course> getApprovedCourses(int recordsPerPage, int pageNum);

    public List<Course> getApprovedCoursesOfTeacher(int recordsPerPage, int pageNum, String teacherName);

    public List<Course> getFailedCoursesOfTeacher(int recordsPerPage, int pageNum, String teacherName);

    public List<Course> getReviewingCoursesOfTeacher(int recordsPerPage, int pageNum, String teacherName);

    List<Course> getAttendedCourseOfUser(String username);
}
