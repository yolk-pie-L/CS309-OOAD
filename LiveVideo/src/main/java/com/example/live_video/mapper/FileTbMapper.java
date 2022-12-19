package com.example.live_video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.live_video.entity.FileTb;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface FileTbMapper extends BaseMapper<FileTb> {

    @Update("update file_tb set f_index=#{fileTb.fIndex,jdbcType=BIGINT} where f_key =#{fileTb.fKey,jdbcType=VARCHAR}")
    void UpdateFile(@Param("fileTb") FileTb fileTb);

    @Select("SELECT id from file_tb f WHERE f.f_key=#{key,jdbcType=VARCHAR} limit 1")
    Integer isExist(@Param("key") String key);

    @Select("SELECT * from file_tb f WHERE f.f_key=#{key,jdbcType=VARCHAR} limit 1\n" +
            "    ")
    FileTb selectLatestIndex(@Param("key") String key);
}
