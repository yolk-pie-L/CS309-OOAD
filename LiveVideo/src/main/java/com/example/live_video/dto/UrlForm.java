package com.example.live_video.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
public class UrlForm {
    private String userName;

    private String userType;

    private String mail;

    private String photoUrl;

    private File file;
}
