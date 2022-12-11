package com.example.live_video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.live_video.entity.Assignment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AssignmentMapper extends BaseMapper<Assignment> {

    @Insert("INSERT INTO assign_urls(assign_id, assign_url)\n" +
            "VALUES (#{assignId}, #{assignUrl})")
    public void insertAssignUrlList(Long assignId, String assignUrl);

    @Delete("DELETE assignment, assign_urls\n" +
            "FROM assignment\n" +
            "         JOIN assign_urls ON assignment.id = assign_urls.assign_id\n" +
            "WHERE assignment.course_id = #{courseId} AND assignment.assignment_name = #{assignName}")
    public int deleteAssignment(Long courseId, String assignName);

    @Select("SELECT assign_url\n" +
            "FROM assign_urls\n" +
            "         JOIN assignment ON assign_urls.assign_id = assignment.id\n" +
            "WHERE assign_urls.assign_id = #{assignId}")
    public List<String> getAssignUrlList(Long assignId);


}
