package com.example.live_video.service.impl;

import com.example.live_video.constance.FileConstance;
import com.example.live_video.entity.FileTb;
import com.example.live_video.mapper.FileTbMapper;
import com.example.live_video.service.FileTbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class FileTbServiceImpl implements FileTbService {


    @Autowired
    private FileTbMapper fileTbMapper;

    public void saveFile(FileTb fileTb) {
        fileTbMapper.insert(fileTb);
    }

    public void updateFile(FileTb fileTb) {
        fileTbMapper.UpdateFile(fileTb);
    }

    public boolean isNotExist(String key){
        Integer id = fileTbMapper.isExist(key);
        if (ObjectUtils.isEmpty(id)) {
            return true;
        }
        return false;
    }
    public FileTb selectLatestIndex(String key) {
        FileTb fileTb = fileTbMapper.selectLatestIndex(key);
        if (ObjectUtils.isEmpty(fileTb)) {
            fileTb = FileTb.builder().fKey(key).fIndex(-1).fName("").build();
        }else {
            fileTb.setFName(FileConstance.ACCESS_PATH+fileTb.getFName());
        }
        return fileTb;
    }
}
