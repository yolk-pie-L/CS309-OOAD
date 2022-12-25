package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.LiveStream;
import com.example.live_video.mapper.LiveStreamMapper;
import com.example.live_video.service.LiveStreamService;
import com.example.live_video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveStreamServiceImpl extends ServiceImpl<LiveStreamMapper, LiveStream> implements LiveStreamService{

    @Autowired
    LiveStreamMapper liveStreamMapper;

    @Autowired
    UserService userService;

    @Override
    public void createLiveStream(LiveStream liveStream) {
        liveStream.setUserId(userService.getUserId(liveStream.getUserName()));
        liveStreamMapper.insert(liveStream);
    }

    @Override
    public List<LiveStream> getLiveStreamList() {
        List<LiveStream> liveStreamList = list();
        for(LiveStream liveStream: liveStreamList){
            liveStream.setUserName(userService.getById(liveStream.getUserId()).getUserName());
        }
        return liveStreamList;
    }

    @Override
    public LiveStream getLiveStream(long id) {
        LiveStream liveStream = liveStreamMapper.selectById(id);
        liveStream.setUserName(userService.getById(liveStream.getUserId()).getUserName());
        return liveStream;
    }
}
