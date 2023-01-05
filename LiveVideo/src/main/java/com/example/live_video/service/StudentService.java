package com.example.live_video.service;

import com.example.live_video.entity.Course;
import com.example.live_video.entity.User;
import com.example.live_video.exception.EnrollCourseException;
import com.example.live_video.exception.MyException;
import com.example.live_video.vo.StudentGradeVo;
import com.example.live_video.vo.StudentSectionProgressVo;

import java.util.List;

public interface StudentService {

    public Boolean enrollCourse(Long courseId, String studentName) throws EnrollCourseException, MyException;

    public Boolean exitCourse(Long courseId, String studentName);

    public List<Course> getEnrolledCourseList(String studentName);

    public List<User> getStudentListOfOneCourse(long courseId);

    public Boolean setStudentAssignGrade(String studentName, Long assignId, int grade);

    public int getStudentAssignGrade(String studentName, Long assignId);

    public Boolean submitAssignment(String studentName, Long assignId, List<String> assignUrls);

    public List<String> getStudentAssignmentUrlList(String studentName, Long assignId);

    public boolean resubmitAssignment(String studentName, Long assignId, List<String> assignUrls);

    public boolean setStudentSectionProgress(long studentId, long sectionId, double ratio);

    public List<StudentSectionProgressVo> getStudentSectionsProgress(long studentId, long courseId);

    /**
     * 老师查看该课程所有学生的成绩
     * @param courseId
     * @return
     */
    public List<StudentGradeVo> getStudentGrades(long courseId);
}
