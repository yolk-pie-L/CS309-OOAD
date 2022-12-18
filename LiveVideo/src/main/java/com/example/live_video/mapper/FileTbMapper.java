package com.example.live_video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.live_video.entity.FileTb;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileTbMapper extends BaseMapper<FileTb> {
    void UpdateFile(@Param("fileTb") FileTb fileTb);

    Integer isExist(@Param("key") String key);

    FileTb selectLatestIndex(@Param("key") String key);
}
