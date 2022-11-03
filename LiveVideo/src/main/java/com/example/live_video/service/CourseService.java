package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.Course;
import com.example.live_video.entity.CourseStatus;
import com.example.live_video.entity.User;
import com.example.live_video.exception.MyException;
import com.example.live_video.exception.SQLCoursenameConflictException;

import java.util.List;

public interface CourseService extends IService<Course> {

    /**
     * Teacher creates his course. After course creation, the course will be inserted in to the
     * database with status REVIEWING
     * @param course the course to be created
     * @return true if the course is created successfully
     * @throws SQLCoursenameConflictException
     */
    public boolean createCourse(Course course) throws MyException;

    /**
     * Update the course. Any field that is not empty in the course will be updated.
     * @param course course to be updated
     * @return true if the course is updated succesfully
     * @throws
     */
    public boolean updateCourse(Course course, User teacher);

    /**
     * Get the course that has status REVIEWING
     * @return a list of courses
     */
    public List<Course> getReviewingCourses();


}
