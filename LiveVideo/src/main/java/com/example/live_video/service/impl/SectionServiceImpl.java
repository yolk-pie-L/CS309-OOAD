package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.Section;
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
    public Boolean createSection(Section section) throws SQLSectionnameConflictException {
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
        QueryWrapper<Section> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("section_name", section.getSectionName());
        sectionQueryWrapper.eq("course_id", section.getCourseId());
        return sectionMapper.update(section, sectionQueryWrapper) == 1;
    }

    @Override
    public Boolean removeSection(Long sectionId) {
        return sectionMapper.deleteById(sectionId) == 1;
    }

    @Override
    public List<Section> getSectionList(Long courseId) {
        QueryWrapper<Section> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("course_id", courseId);
        return sectionMapper.selectList(sectionQueryWrapper);
    }

    @Override
    public Section getOneSection(Long sectionId) {
        return sectionMapper.selectById(sectionId);
    }

    @Override
    public Section getOneSection(Long courseId, String sectionName) {
        // TODO: 获取课程中的小节
        return null;
    }

    @Override
    public Boolean uploadVideo(Long courseId, String sectionName, String videoUrl) {
        // TODO: 上次视频URL
        return null;
    }
}
