package com.example.live_video.vo;

import com.example.live_video.entity.Assignment;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class AssignmentVo {
    private Long id;
    private String assignmentName;
    private Long courseId;
    private String courseName;
    private String teacherName;
    private Timestamp deadline;
    private Integer totalGrade;
    private List<String> assignUrls;
    private Boolean isAssignment = true;
    private String description;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String status;
    private Integer score;
    private List<String> attached;
    private List<String> answer;
    private int limitedTime;

    public AssignmentVo(Long id, String assignmentName, Long courseId, String courseName, String teacherName,
                        Timestamp deadline, Integer totalGrade, List<String> assignUrls, Boolean isAssignment,
                        String description, Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.deadline = deadline;
        this.totalGrade = totalGrade;
        this.assignUrls = assignUrls;
        this.isAssignment = isAssignment;
        this.description = description;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public static AssignmentVo parse(Assignment assignment) {
        return new AssignmentVo(
                assignment.getId(),
                assignment.getAssignmentName(),
                assignment.getCourseId(),
                assignment.getCourseName(),
                assignment.getTeacherName(),
                assignment.getDeadline(),
                assignment.getTotalGrade(),
                assignment.getAssignUrls(),
                assignment.getIsAssignment(),
                assignment.getDescription(),
                assignment.getCreateTime(),
                assignment.getUpdateTime());
    }

    public AssignmentVo(Long id, String assignmentName, Timestamp deadline, String status) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.status = status;
    }

    public static AssignmentVo parseInfo(Assignment assignment) {
        return new AssignmentVo(
                assignment.getId(),
                assignment.getAssignmentName(),
                assignment.getDeadline(),
                "null"
        );
    }

    public static List<AssignmentVo> parseInfo(List<Assignment> assignmentList) {
        List<AssignmentVo> assignmentVoList = new ArrayList<>();
        assignmentList.forEach(assign -> assignmentVoList.add(AssignmentVo.parseInfo(assign)));
        return assignmentVoList;
    }

    public static Assignment voToAssign(AssignmentVo assignmentVo) {
        return new Assignment(
                assignmentVo.getAssignmentName(),
                assignmentVo.getCourseName(),
                assignmentVo.getTeacherName(),
                assignmentVo.getDeadline(),
                assignmentVo.getTotalGrade(),
                assignmentVo.getIsAssignment(),
                assignmentVo.getDescription(),
                assignmentVo.getAssignUrls()
        );
    }

    public AssignmentVo(String assignmentName, Long courseId, Timestamp deadline, Integer totalGrade,
                        String description, List<String> attached, int limitedTime) {
        this.assignmentName = assignmentName;
        this.courseId = courseId;
        this.deadline = deadline;
        this.totalGrade = totalGrade;
        this.description = description;
        this.attached = attached;
        this.limitedTime = limitedTime;
    }
}
