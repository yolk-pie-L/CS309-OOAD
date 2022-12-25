package com.example.live_video;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.example.live_video.mapper")
@EnableCaching
public class LiveVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiveVideoApplication.class, args);
    }

}
