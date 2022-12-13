package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.Section;
import com.example.live_video.exception.MyException;
import com.example.live_video.exception.SQLSectionnameConflictException;

import java.io.IOException;
import java.util.List;

public interface SectionService extends IService<Section> {

    public Boolean createSection(Section section);

    public Boolean updateSection(Section section);

    public Boolean removeSection(Long sectionId);

    public List<Section> getSectionList(Long courseId);

    public Section getOneSection(Long sectionId);

    Long getSectionId(Long courseId, String sectionName);

    String uploadSlice(byte[] bytes, String hash, String filename, Integer seq, String type) throws IOException;

    String uploadMerge(String filename, String type, String hash) throws MyException, Exception;
}
