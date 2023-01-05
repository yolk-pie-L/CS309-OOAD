package com.example.live_video.service.impl;

import com.example.live_video.entity.Assignment;
import com.example.live_video.entity.Course;
import com.example.live_video.entity.Section;
import com.example.live_video.entity.User;
import com.example.live_video.exception.EnrollCourseException;
import com.example.live_video.exception.MyException;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.SectionMapper;
import com.example.live_video.mapper.StudentMapper;
import com.example.live_video.mapper.UserMapper;
import com.example.live_video.service.AssignmentService;
import com.example.live_video.service.SectionService;
import com.example.live_video.service.StudentService;
import com.example.live_video.service.UserService;
import com.example.live_video.vo.StudentAssignGradeVo;
import com.example.live_video.vo.StudentGradeVo;
import com.example.live_video.vo.StudentSectionGradeVo;
import com.example.live_video.vo.StudentSectionProgressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    SectionMapper sectionMapper;

    @Autowired
    UserService userService;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    SectionService sectionService;

    @Override
    @Transactional
    public Boolean enrollCourse(Long courseId, String studentName) throws MyException {
        Long studentId = userService.getUserId(studentName);
        Long courseCharge = courseMapper.getCourseCharge(courseId);
        Long studentAccount = userService.getUserAccount(studentName);
        if (studentMapper.getRelationship(studentId, courseId)) {
            throw new MyException("已加入课程");
        }
        if (courseCharge <= studentAccount) {
            User user = new User(studentName, null, null, null, null,
                    studentAccount - courseCharge);
            userService.updateUser(user);
            studentMapper.enrollCourse(studentId, courseId);
            return true;
        }
        throw new EnrollCourseException("student account not enough");
    }

    @Override
    public Boolean exitCourse(Long courseId, String studentName) {
        Long studentId = userService.getUserId(studentName);
        studentMapper.exitCourse(studentId, courseId);
        return true;
    }

    @Override
    public List<Course> getEnrolledCourseList(String studentName) {
        Long studentId = userService.getUserId(studentName);
        List<Course> courses = studentMapper.getEnrolledCourseList(studentId);
        return courses;
    }

    @Override
    public List<User> getStudentListOfOneCourse(long courseId) {
        return studentMapper.getStudentListOfOneCourse(courseId);
    }

    @Override
    public Boolean setStudentAssignGrade(String studentName, Long assignId, int grade) {
        Long studentId = userService.getUserId(studentName);
        studentMapper.setStudentAssignGrade(studentId, assignId, grade);
        return true;
    }

    @Override
    public int getStudentAssignGrade(String studentName, Long assignId) {
        Long studentId = userService.getUserId(studentName);
        return studentMapper.getStudentAssignGrade(studentId, assignId);
    }

    @Override
    @Transactional
    public Boolean submitAssignment(String studentName, Long assignId, List<String> assignUrls) {
        Long studentId = userService.getUserId(studentName);
        for (String assignUrl : assignUrls) {
            studentMapper.submitAssignment(studentId, assignId, assignUrl);
        }
        return true;
    }

    @Override
    public List<String> getStudentAssignmentUrlList(String studentName, Long assignId) {
        Long studentId = userService.getUserId(studentName);
        List<String> assignUrls = studentMapper.getStudentSubmittedAssignUrlList(studentId, assignId);
        return assignUrls;
    }

    @Override
    @Transactional
    public boolean resubmitAssignment(String studentName, Long assignId, List<String> assignUrls) {
        Long studentId = userService.getUserId(studentName);
        studentMapper.deleteStudentAssignment(studentId, assignId);
        return this.submitAssignment(studentName, assignId, assignUrls);
    }

    @Override
    public boolean setStudentSectionProgress(long studentId, long sectionId, double ratio) {
        Section section = sectionMapper.selectById(sectionId);
        double studentGrade = section.getGrade() * ratio;
        studentMapper.setStudentSectionProgress(studentId, sectionId, studentGrade);
        return true;
    }

    @Override
    public List<StudentSectionProgressVo> getStudentSectionsProgress(long studentId, long courseId) {
        List<Section> sectionList = sectionService.getSectionList(courseId);
        User student = userService.getById(studentId);
        List<StudentSectionProgressVo> studentSectionProgressList = new LinkedList<>();
        for (Section section : sectionList) {
            StudentSectionProgressVo studentSectionProgress = new StudentSectionProgressVo();
            studentSectionProgress.setSectionName(section.getSectionName());
            int studentGrade = studentMapper.getStudentSectionGrade(student.getId(), section.getId());
            studentSectionProgress.setStudentGrade(studentGrade);
            studentSectionProgress.setStudentProgress((double) studentGrade / section.getGrade());
            studentSectionProgressList.add(studentSectionProgress);
        }
        return studentSectionProgressList;
    }


    @Override
    public List<StudentGradeVo> getStudentGrades(long courseId) {
        List<User> studentList = getStudentListOfOneCourse(courseId);
        List<StudentGradeVo> studentGradesList = new LinkedList<>();
        List<Assignment> assignmentList = assignmentService.getAssignmentsOfCourse(courseId);
        List<Section> sectionList = sectionService.getSectionList(courseId);
        for (User student : studentList) {
            StudentGradeVo studentGradeVo = new StudentGradeVo();
            studentGradeVo.setStudentName(student.getUserName());

            List<StudentAssignGradeVo> studentAssignGradeList = new LinkedList<>();
            for (Assignment assignment : assignmentList) {
                StudentAssignGradeVo studentAssignGrade = new StudentAssignGradeVo();
                studentAssignGrade.setAssignName(assignment.getAssignmentName());
                studentAssignGrade.setTotalGrade(assignment.getTotalGrade());
                studentAssignGrade.setStudentGrade(getStudentAssignGrade(student.getUserName(), assignment.getId()));
                studentAssignGradeList.add(studentAssignGrade);
            }
            studentGradeVo.setStudentAssignGradeList(studentAssignGradeList);

            List<StudentSectionGradeVo> studentSectionGradeList = new LinkedList<>();
            for (Section section : sectionList) {
                StudentSectionGradeVo studentSectionGrade = new StudentSectionGradeVo();
                studentSectionGrade.setSectionName(section.getSectionName());
                studentSectionGrade.setFullGrade(section.getGrade());
                studentSectionGrade.setStudentGrade(studentMapper.getStudentSectionGrade(student.getId(), section.getId()));
                studentSectionGradeList.add(studentSectionGrade);
            }
            studentGradeVo.setStudentSectionGradeList(studentSectionGradeList);

            studentGradesList.add(studentGradeVo);
        }
        return studentGradesList;
    }
}
