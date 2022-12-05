package com.example.live_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.live_video.entity.Course;
import com.example.live_video.service.VideoService;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class VideoServiceImpl implements VideoService {

    @Override
    public String getVideoUrl(String courseName, String sectionName) {
        return null;
    }

    @Override
    public Boolean upload(String courseName, String sectionName) {
        return null;
    }

    @Override
    public boolean saveBatch(Collection<Course> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<Course> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<Course> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(Course entity) {
        return false;
    }

    @Override
    public Course getOne(Wrapper<Course> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<Course> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<Course> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<Course> getBaseMapper() {
        return null;
    }

    @Override
    public Class<Course> getEntityClass() {
        return null;
    }
}
