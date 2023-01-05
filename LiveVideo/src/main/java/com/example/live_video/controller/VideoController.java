package com.example.live_video.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.live_video.constance.FileConstance;
import com.example.live_video.dto.FileForm;
import com.example.live_video.dto.MergeInfo;
import com.example.live_video.dto.SectionProgressForm;
import com.example.live_video.entity.FileTb;
import com.example.live_video.entity.StuSection;
import com.example.live_video.service.*;
import com.example.live_video.vo.StringVo;
import com.example.live_video.wrapper.NonStaticResourceHttpRequestHandler;
import com.example.live_video.entity.Section;
import com.example.live_video.exception.MyException;
import com.example.live_video.util.RandomUtils;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import com.example.live_video.wrapper.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
@UserLoginToken
@Slf4j
@RequestMapping("/api/section")
public class VideoController {

    @Value("${src.video-path}")
    private String defaultPath;

    private final String[] extList = new String[]{"mp4", "avi", "mkv", "wmv"};

    @Autowired
    private NonStaticResourceHttpRequestHandler requestHandler;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private FileTbService fileTbService;

    @Autowired
    UserService userService;

    @Autowired
    private StuSectionService stuSectionService;

    @PostMapping("/update")
    public void updateSectionProgress(@RequestBody SectionProgressForm sectionProgressForm) {
        QueryWrapper<StuSection> stuSectionQueryWrapper = new QueryWrapper<>();
        Long userId = userService.getUserId(sectionProgressForm.getStudentName());
        stuSectionQueryWrapper.eq("user_id", userId);
        stuSectionQueryWrapper.eq("section_id", sectionProgressForm.getSectionId());
        Section section = sectionService.getOneSection(sectionProgressForm.getSectionId());
        StuSection stuSection = new StuSection(userId, sectionProgressForm.getSectionId(),
                Math.min((int) (sectionProgressForm.getTotalWatch() / sectionProgressForm.getVideoTime()  * 1.1 * section.getGrade()), section.getGrade()),
                sectionProgressForm.getTotalWatch(), sectionProgressForm.getCurrentWatch());
        stuSectionService.update(stuSection, stuSectionQueryWrapper);
    }

    @GetMapping("/getprogress")
    public StuSection getProgress(@RequestParam String studentName, @RequestParam Long sectionId) {
        QueryWrapper<StuSection> stuSectionQueryWrapper = new QueryWrapper<>();
        Long userId = userService.getUserId(studentName);
        stuSectionQueryWrapper.eq("user_id", userId);
        stuSectionQueryWrapper.eq("section_id", sectionId);
        return stuSectionService.getOne(stuSectionQueryWrapper);
    }

    @GetMapping("/{sectionId}")
    public void videoPreview(HttpServletRequest request, HttpServletResponse response, @PathVariable String sectionId) throws Exception {
        String videoUrl = sectionService.getOneSection(Long.valueOf(sectionId)).getVideoUrl();

        System.out.println(videoUrl);
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
        String fileExt = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
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
        Section section = new Section(secName, courseId, filePath.getPath(), 0);
        Long id = sectionService.getSectionId(courseId, secName);
        if (id == -1) {
            sectionService.createSection(section);
            id = section.getId();
        } else {
            throw new MyException("视频已存在，请先删除先前视频");
        }
        return sectionService.getOneSection(id);
    }

    @PostMapping(value = "/upload2")
    public StringVo upload(@RequestParam(value = "file") MultipartFile file,
                           FileForm fileForm) throws Exception {
        File fullDir = new File(FileConstance.FILE_PATH);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }

        //uid 防止文件名重复,又可以作为文件的唯一标识
        String fullPath = FileConstance.FILE_PATH + fileForm.getKey() + "." + fileForm.getShardIndex();
        File dest = new File(fullPath);
        file.transferTo(dest);
        log.info("文件分片 {} 保存完成", fileForm.getShardIndex());

        //开始保存索引分片信息 bu不存在就新加 存在就修改索引分片
        FileTb fileTb = FileTb.builder()
                .fKey(fileForm.getKey())
                .fIndex(Math.toIntExact(fileForm.getShardIndex()))
                .fTotal(Math.toIntExact(fileForm.getShardTotal()))
                .fName(fileForm.getFileName())
                .build();
        if (fileTbService.isNotExist(fileForm.getKey())) {
            fileTbService.saveFile(fileTb);
        } else {
            fileTbService.updateFile(fileTb);
        }


        if (Objects.equals(fileForm.getShardIndex(), fileForm.getShardTotal())) {
            //开始合并
            System.out.println("FileForm: "+fileForm);
            merge(fileForm);
            return new StringVo(FileConstance.ACCESS_PATH + fileForm.getFileName());
        }
        return new StringVo("OK");
    }

    public Long merge(FileForm fileForm) throws Exception {
        Long shardTotal = fileForm.getShardTotal();
        File newFile = new File(FileConstance.FILE_PATH + fileForm.getFileName());
        if (newFile.exists()) {
            newFile.delete();
        }
        FileOutputStream outputStream = new FileOutputStream(newFile, true);//文件追加写入
        System.out.println(newFile.getCanonicalPath());
        Section section = new Section(fileForm.getSectionName(), fileForm.getCourseId(), newFile.getCanonicalPath(), 0);
        Long id = sectionService.getSectionId(fileForm.getCourseId(), fileForm.getSectionName());
        if (id == -1) {
            sectionService.createSection(section);
            id = section.getId();
        } else {
            throw new MyException("视频已存在，请先删除先前视频");
        }
        FileInputStream fileInputStream = null;//分片文件
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;
        try {
            for (int i = 0; i < shardTotal; i++) {
                // 读取第i个分片
                fileInputStream = new FileInputStream(FileConstance.FILE_PATH + fileForm.getKey() + "." + (i + 1)); //  course\6sfSqfOwzmik4A4icMYuUe.mp4.1
                while ((len = fileInputStream.read(byt)) != -1) {
                    outputStream.write(byt, 0, len);//一直追加到合并的新文件中
                }
            }
        } catch (IOException e) {
            log.error("分片合并异常", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                outputStream.close();
                log.info("IO流关闭");
                System.gc();
            } catch (Exception e) {
                log.error("IO流关闭", e);
            }
        }
        log.info("合并分片结束");
        return id;
    }

    //文件上传之前判断是否已经上传过 -1就是没有
    @GetMapping("/check")
    public FileTb check(@RequestParam String key) {
        FileTb fileTb = fileTbService.selectLatestIndex(key);
        log.info("检查分片：{}");
        return fileTb;
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
