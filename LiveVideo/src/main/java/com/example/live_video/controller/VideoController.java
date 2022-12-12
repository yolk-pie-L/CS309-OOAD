package com.example.live_video.controller;

import com.example.live_video.wrapper.NonStaticResourceHttpRequestHandler;
import com.example.live_video.entity.Course;
import com.example.live_video.entity.Section;
import com.example.live_video.exception.MyException;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.SectionService;
import com.example.live_video.util.RandomUtils;
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
import java.util.List;

import static com.example.live_video.wrapper.NonStaticResourceHttpRequestHandler.ATTR_FILE;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/video")
public class VideoController {

    private final String defaultPath = "src/main/resources/static/video/";

    private final String[] extList = new String[]{".mp4", ".avi", ".mkv", ".wmv"};

    @Autowired
    private NonStaticResourceHttpRequestHandler requestHandler;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/{sectionId}")
    public void videoPreview(HttpServletRequest request, HttpServletResponse response, @PathVariable Long sectionId) throws Exception {
//        String videoUrl = sectionService.getOneSection(sectionId).getVideoUrl();
        System.out.println("Hello world");
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

    @PostMapping("/upload")
    public Boolean saveVideo(@RequestParam MultipartFile file,
                             @RequestParam Long courseId,
                             @RequestParam String secName) throws Exception {
        // 获取后缀名
        Course course = courseService.getOneCourse(courseId);
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1).toLowerCase();
        if (!Arrays.asList(extList).contains(fileExt)) {
            throw new MyException("视频格式不正确");
        }

        String savePath = defaultPath + '/';
        String videoName = RandomUtils.VID(10) + fileExt;
        File filePath = new File(savePath, videoName);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }
        Section section = new Section(secName, course.getCourseName(), course.getTeacherName(), filePath.getPath());
        sectionService.createSection(section);
        return true;
    }

    @PostMapping("remove/{sectionId}")
    public Boolean remove(@PathVariable Long sectionId) {
        sectionService.removeSection(sectionId);
        return true;
    }

    @GetMapping("all/{courseId}")
    public List<Section> getSectionList(@PathVariable Long courseId) {
        return sectionService.getSectionList(courseId);
    }
}
