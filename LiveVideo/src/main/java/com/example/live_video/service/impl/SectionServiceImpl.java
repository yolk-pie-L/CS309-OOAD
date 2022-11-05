package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.Section;
import com.example.live_video.exception.MyException;
import com.example.live_video.exception.SQLSectionnameConflictException;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.SectionMapper;
import com.example.live_video.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionServiceImpl extends ServiceImpl<SectionMapper, Section> implements SectionService {

    @Autowired
    SectionMapper sectionMapper;

    @Autowired
    CourseMapper courseMapper;

    @Override
    public Boolean createSection(Section section) throws MyException {
        Long courseId = courseMapper.getCourseIdByTeacherNameCourseName(section.getTeacherName(), section.getCourseName());
        section.setCourseId(courseId);
        QueryWrapper<Section> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("section_name", section.getSectionName());
        sectionQueryWrapper.eq("course_id", section.getCourseId());
        if(sectionMapper.exists(sectionQueryWrapper)){
            throw new SQLSectionnameConflictException();
        }
        return sectionMapper.insert(section) == 1;
    }

    @Override
    public Boolean updateSection(Section section) {
        Long courseId = courseMapper.getCourseIdByTeacherNameCourseName(section.getTeacherName(), section.getCourseName());
        section.setCourseId(courseId);
        QueryWrapper<Section> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("section_name", section.getSectionName());
        sectionQueryWrapper.eq("course_id", section.getCourseId());
        return sectionMapper.update(section, sectionQueryWrapper) == 1;
    }

    @Override
    public Boolean removeSection(String teacherName, String courseName, String sectionName) {
        Long courseId = courseMapper.getCourseIdByTeacherNameCourseName(teacherName, courseName);
        QueryWrapper<Section> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("section_name", sectionName);
        sectionQueryWrapper.eq("course_id", courseId);
        return sectionMapper.delete(sectionQueryWrapper) == 1;
    }

    @Override
    public List<Section> getAllSections(String teacherName, String courseName) {
        Long courseId = courseMapper.getCourseIdByTeacherNameCourseName(teacherName, courseName);
        QueryWrapper<Section> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("course_id", courseId);
        return sectionMapper.selectList(sectionQueryWrapper);
    }
}
