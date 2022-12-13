package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.Section;
import com.example.live_video.exception.MyException;
import com.example.live_video.exception.SQLSectionnameConflictException;
import com.example.live_video.mapper.CourseMapper;
import com.example.live_video.mapper.SectionMapper;
import com.example.live_video.service.SectionService;
import com.example.live_video.util.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.List;

@Service
public class SectionServiceImpl extends ServiceImpl<SectionMapper, Section> implements SectionService {

    @Autowired
    SectionMapper sectionMapper;

    @Autowired
    CourseMapper courseMapper;

    @Value("src.path")
    String BASE_DIR;

    @Override
    public Boolean createSection(Section section) {
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
    public Long getSectionId(Long courseId, String sectionName) {
        QueryWrapper<Section> sectionQueryWrapper = new QueryWrapper<>();
        sectionQueryWrapper.eq("course_id", courseId);
        sectionQueryWrapper.eq("section_name", sectionName);
        Section section = sectionMapper.selectOne(sectionQueryWrapper);
        if(section == null){
            return -1L;
        }else{
            return section.getId();
        }
    }

    @Override
    public String uploadSlice(byte[] bytes, String hash, String filename, Integer seq, String type) throws IOException {
        RandomAccessFile raf;
        File dir = new File(BASE_DIR + hash);
        if (!dir.exists())
            dir.mkdirs();
        raf = new RandomAccessFile(BASE_DIR + hash + "\\" + filename + "." + type + seq, "rw");
        raf.write(bytes);
        return "OK";
    }

    @Override
    public String uploadMerge(String filename, String type, String hash) throws Exception {
        File dir = new File(BASE_DIR + hash);
        if (!dir.exists()) {
            throw new MyException("合并失败，请稍后再试");
        }


        FileChannel in = new RandomAccessFile(BASE_DIR + filename + '.' + type, "rw").getChannel();
        int index = 1;
        long start = 0;
        while (true) {
            String sliceName = BASE_DIR + hash + "\\" + filename + "." + type + index;
            if (!new File(sliceName).exists())
                break;
            FileChannel out = new RandomAccessFile(sliceName, "r").getChannel();
            in.transferFrom(out, start, start + out.size());
            start += out.size();
            out.close();
            index++;
        }
        in.close();
        return "上传成功";
    }
}
