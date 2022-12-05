package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.Course;

public interface VideoService extends IService<Course> {

    String getVideoUrl(String courseName, String sectionName);

    Boolean upload(String courseName, String sectionName);
}
