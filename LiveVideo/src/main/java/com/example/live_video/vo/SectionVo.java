package com.example.live_video.vo;

import com.example.live_video.entity.Section;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SectionVo {

    private Long sectionIdIn;

    private String sectionName;

    private String sectionComplete;

    private String status;

    public static SectionVo parse(Section section) {
        SectionVo sectionVo = new SectionVo();
        sectionVo.setSectionIdIn(section.getId());
        sectionVo.setSectionName(section.getSectionName()   );
        sectionVo.setSectionComplete("0%");
        return sectionVo;
    }

    public static List<SectionVo> parse(List<Section> sectionList) {
        List<SectionVo> sectionVoList = new ArrayList<>();
        sectionList.forEach(section -> sectionVoList.add(SectionVo.parse(section)));
        return sectionVoList;
    }
}
