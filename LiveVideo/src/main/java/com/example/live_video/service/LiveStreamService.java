package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.LiveStream;

import java.util.List;

public interface LiveStreamService extends IService<LiveStream> {

    public void createLiveStream(LiveStream liveStream);

    public List<LiveStream> getLiveStreamList();

    public LiveStream getLiveStream(long id);

}
