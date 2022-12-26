package com.example.live_video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.live_video.entity.LiveStream;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LiveStreamMapper extends BaseMapper<LiveStream> {
}
