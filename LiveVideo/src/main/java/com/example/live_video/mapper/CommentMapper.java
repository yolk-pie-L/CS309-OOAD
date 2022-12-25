package com.example.live_video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.live_video.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
