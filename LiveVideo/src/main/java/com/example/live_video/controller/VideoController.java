package com.example.live_video.controller;

import com.example.live_video.config.NonStaticResourceHttpRequestHandler;
import com.example.live_video.exception.MyException;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.example.live_video.config.NonStaticResourceHttpRequestHandler.ATTR_FILE;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/video")
public class VideoController {

    private final String defaultPath = "src/main/resources/static/video/";

    private final String[] extList = new String[]{".mp4", ".avi", ".mkv", ".wmv"};

    @Autowired
    private NonStaticResourceHttpRequestHandler requestHandler;

    @Autowired(required = false)
    private VideoService videoService;

    @GetMapping("")
    public void videoPreview(HttpServletRequest request, HttpServletResponse response, @RequestParam String courseName, @RequestParam String sectionName) throws Exception {
//        String videoUrl = videoService.getVideoUrl(courseName, sectionName);
        String videoUrl = "src/main/resources/static/video/demo1.mp4";

        Path filePath = Paths.get(videoUrl);

        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (StringUtils.hasText(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(ATTR_FILE, videoUrl);
            requestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }

    @PostMapping("")
    public Boolean saveVideo(@RequestParam MultipartFile file,
                             @RequestParam String courseName,
                             @RequestParam String sectionName) throws Exception {
        // 获取后缀名
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1).toLowerCase();
        if (!Arrays.asList(extList).contains(fileExt)) {
            throw new MyException("视频格式不正确");
        }

        String savePath = defaultPath + courseName + '/' + sectionName + '/';
        String videoName = "ababa" + fileExt;
        File filePath = new File(savePath, videoName);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }
        return true;
    }

}
