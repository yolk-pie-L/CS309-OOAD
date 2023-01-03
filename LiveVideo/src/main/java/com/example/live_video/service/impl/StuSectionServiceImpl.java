package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.live_video.entity.StuSection;
import com.example.live_video.mapper.StuSectionMapper;
import com.example.live_video.service.StuSectionService;
import org.springframework.stereotype.Service;

@Service
public class StuSectionServiceImpl extends ServiceImpl<StuSectionMapper, StuSection> implements StuSectionService {
}
