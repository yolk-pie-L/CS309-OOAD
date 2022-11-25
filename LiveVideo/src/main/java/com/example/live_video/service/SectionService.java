package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.Section;
import com.example.live_video.exception.SQLSectionnameConflictException;

import java.util.List;

public interface SectionService extends IService<Section> {

    public Boolean createSection(Section section) throws SQLSectionnameConflictException;

    public Boolean updateSection(Section section);

    public Boolean removeSection(String teacherName, String courseName, String sectionName);

    public List<Section> getAllSections(String teacherName, String courseName);
}
