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

    public AssignmentVo(Long id, String assignmentName, Long courseId, String courseName, String teacherName,
                        Timestamp deadline, Integer totalGrade, List<String> assignUrls, Boolean isAssignment,
                        String description, Timestamp createTime, Timestamp updateTime, String status) {
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
    }

    public static AssignmentVo parse(Assignment assignment) {
        String status = "Not Started";  // fixme
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
                status
        );
    }

    public static List<AssignmentVo> parse(List<Assignment> assignmentList) {
        List<AssignmentVo> assignmentVoList = new ArrayList<>();
        assignmentList.forEach(assign -> assignmentVoList.add(AssignmentVo.parse(assign)));
        return assignmentVoList;
    }
}