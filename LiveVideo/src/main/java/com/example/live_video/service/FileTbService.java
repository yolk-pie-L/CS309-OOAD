package com.example.live_video.service;

import com.example.live_video.entity.FileTb;

public interface FileTbService {

    void saveFile(FileTb fileTb);

    void updateFile(FileTb fileTb);

    boolean isNotExist(String key);

    FileTb selectLatestIndex(String key);
}
