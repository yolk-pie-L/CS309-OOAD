package com.example.live_video;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.live_video.mapper")
public class LiveVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiveVideoApplication.class, args);
    }

}
