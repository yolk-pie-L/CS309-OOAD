package com.example.live_video.controller;

import com.example.live_video.dto.DeleteLiveStreamForm;
import com.example.live_video.dto.LiveStreamForm;
import com.example.live_video.entity.LiveStream;
import com.example.live_video.service.LiveStreamService;
import com.example.live_video.wrapper.PassToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PassToken
@RequestMapping("/api/livestream")
public class LiveStreamController {

    @Autowired
    LiveStreamService liveStreamService;

    @GetMapping("/all")
    List<LiveStream> getAllLiveSteam(){
        return liveStreamService.getLiveStreamList();
    }

    @GetMapping("/{id}")
    LiveStream getOneLiveStream(@PathVariable Long id){
        return liveStreamService.getLiveStream(id);
    }

    @PostMapping("/create")
    boolean createLiveStream(@RequestBody LiveStreamForm liveStreamForm){
        LiveStream liveStream = new LiveStream(liveStreamForm.getTitle(), liveStreamForm.getUserName(),
                liveStreamForm.getUrl());
        liveStreamService.createLiveStream(liveStream);
        return true;
    }

    @PostMapping("/stop")
    boolean deleteLiveStream(@RequestBody DeleteLiveStreamForm deleteLiveStreamForm){
        liveStreamService.removeById(deleteLiveStreamForm.getId());
        return true;
    }

}
