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
    public Boolean createCourse(Course course) throws SQLCoursenameConflictException;


    /**
     * Update the course. Any field that is not empty in the course will be updated.
     * @param course course to be updated
     * @return true if the course is updated successfully
     * @throws
     */
    public boolean updateCourse(Course course);

    public boolean removeCourse(String teacherName, String courseName);

    /**
     * Get the courses that has status REVIEWING.
     *
     * @return A list of course
     */
    public List<Course> getReviewingCourseList();


    /**
     * Get the courses that has status APPROVED. 实现了分页
     * @param recordsPerPage 每一页的记录数量
     * @param pageNum 第几页（从1开始）
     * @return A list of course
     */
    public List<Course> getApprovedCourseList(int recordsPerPage, int pageNum);

    public List<Course> getApprovedCourseListOfTeacher(int recordsPerPage, int pageNum, String teacherName);

    public List<Course> getFailedCourseListOfTeacher(int recordsPerPage, int pageNum, String teacherName);

    public List<Course> getReviewingCourseListOfTeacher(int recordsPerPage, int pageNum, String teacherName);

    public List<Course> getRegisteredCourseListOfStudent(int recodesPerPage, int pageNum, String stduentName);

    public Course getOneCourse(String teacherName, String courseName);

    public Course getOneCourse(Long id);

    public Course getOneApprovedCourse(String teacherName, String courseName);

    public String getCoursePrivateKeyUrl(String teacherName, String courseName);

    public List<Course> getApprovedCourseList(int recordsPerPage, int pageNum, String courseName);

}
