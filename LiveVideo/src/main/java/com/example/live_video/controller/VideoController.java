package com.example.live_video.controller;

import com.example.live_video.util.MergeInfo;
import com.example.live_video.wrapper.NonStaticResourceHttpRequestHandler;
import com.example.live_video.entity.Course;
import com.example.live_video.entity.Section;
import com.example.live_video.exception.MyException;
import com.example.live_video.service.CourseService;
import com.example.live_video.service.SectionService;
import com.example.live_video.util.RandomUtils;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.live_video.wrapper.NonStaticResourceHttpRequestHandler.ATTR_FILE;

@ResponseResult
@RestController
@PassToken
@RequestMapping("/api/section")
public class VideoController {

    @Value("${src.path}")
    private String defaultPath;

    private final String[] extList = new String[]{"mp4", "avi", "mkv", "wmv"};

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
    public Section saveVideo(@RequestParam MultipartFile file,
                             @RequestParam Long courseId,
                             @RequestParam String secName) throws Exception {
        // 获取后缀名
        String fileExt = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".")+1).toLowerCase();
        if (!Arrays.asList(extList).contains(fileExt)) {
            throw new MyException("视频格式不正确");
        }

        String videoName = RandomUtils.VID(10) + "." + fileExt;
        System.out.println(defaultPath);
        File filePath = new File(defaultPath, videoName);
        System.out.println(filePath.getCanonicalPath());
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }
        file.transferTo(filePath.getCanonicalFile());
        Section section = new Section(secName, courseId, filePath.getPath());
        Long id = sectionService.getId(courseId, secName);
        if (id == -1) {
            sectionService.createSection(section);
            id = sectionService.getId(courseId, secName);
        } else {
            throw new MyException("视频已存在，请先删除先前视频");
        }
        return sectionService.getOneSection(id);
    }

    @PostMapping("/upload2")
    public String uploadSlice(@RequestParam(value = "file") MultipartFile file,
                                          @RequestParam(value = "hash") String hash,
                                          @RequestParam(value = "filename") String filename,
                                          @RequestParam(value = "seq") Integer seq,
                                          @RequestParam(value = "type") String type) throws Exception {
        return sectionService.uploadSlice(file.getBytes(), hash, filename, seq, type);
    }

    @PostMapping("/merge")
    public String merge(@RequestBody MergeInfo mergeInfo) throws Exception {
        if (mergeInfo!=null) {
            String filename = mergeInfo.getFilename();
            String type = mergeInfo.getType();
            String hash = mergeInfo.getHash();
            return sectionService.uploadMerge(filename, type, hash);
        }
        throw new MyException("文件合并失败");
    }

    @PostMapping("/remove/{sectionId}")
    public Boolean remove(@PathVariable Long sectionId) {
        Section section = sectionService.getOneSection(sectionId);
        File filePath = new File(section.getVideoUrl());
        FileSystemUtils.deleteRecursively(filePath);
        sectionService.removeSection(sectionId);
        return true;
    }

    @GetMapping("all/{courseId}")
    public List<Section> getSectionList(@PathVariable Long courseId) {
        return sectionService.getSectionList(courseId);
    }
}
