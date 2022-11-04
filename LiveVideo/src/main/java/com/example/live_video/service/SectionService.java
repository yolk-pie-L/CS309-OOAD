package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.Section;

public interface SectionService extends IService<Section> {

    public Boolean createSection(Section section);

    public Boolean updateSection(Section section);

    public Boolean removeSection(Section section);
}
