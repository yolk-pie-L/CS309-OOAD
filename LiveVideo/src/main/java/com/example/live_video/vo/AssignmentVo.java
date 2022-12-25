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
    private Boolean isAssignment;
    private String description;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String status;
    private List<Attached> attached;
    private List<Attached> answer;

    public AssignmentVo(Long id, String assignmentName, Long courseId, String courseName, String teacherName,
                        Timestamp deadline, Integer totalGrade, List<String> assignUrls, Boolean isAssignment,
                        String description, Timestamp createTime, Timestamp updateTime,
                        String status, List<Attached> attached, List<Attached> answer) {
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
        this.status = status;
        this.attached = attached;
        this.answer = answer;
    }

    public static AssignmentVo parse(Assignment assignment) {
        String status = getStatus(assignment);
        List<Attached> attached = getAttached(assignment);
        List<Attached> answer = getAnswer(assignment);
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
                assignment.getUpdateTime(),
                status,
                attached,
                answer
        );
    }

    public static List<AssignmentVo> parse(List<Assignment> assignmentList) {
        List<AssignmentVo> assignmentVoList = new ArrayList<>();
        assignmentList.forEach(assign -> assignmentVoList.add(AssignmentVo.parse(assign)));
        return assignmentVoList;
    }

    public AssignmentVo(Long id, String assignmentName, Timestamp deadline, String status) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.status = status;
    }

    public static AssignmentVo parseInfo(Assignment assignment) {
        String status = getStatus(assignment);
        return new AssignmentVo(
                assignment.getId(),
                assignment.getAssignmentName(),
                assignment.getDeadline(),
                status
        );
    }

    public static List<AssignmentVo> parseInfo(List<Assignment> assignmentList) {
        List<AssignmentVo> assignmentVoList = new ArrayList<>();
        assignmentList.forEach(assign -> assignmentVoList.add(AssignmentVo.parseInfo(assign)));
        return assignmentVoList;
    }


    private static String getStatus(Assignment a) {
        return "Not finished.";
    }

    private static List<Attached> getAttached(Assignment a) {
        return null;
    }

    private static List<Attached> getAnswer(Assignment a) {
        return null;
    }

    private static class Attached {
        String name;
        String url;
    }
}
