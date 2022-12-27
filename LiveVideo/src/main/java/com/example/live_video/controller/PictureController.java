package com.example.live_video.controller;

import com.example.live_video.wrapper.NonStaticResourceHttpRequestHandler;
import com.example.live_video.wrapper.PassToken;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.live_video.wrapper.NonStaticResourceHttpRequestHandler.ATTR_FILE;

@ResponseResult
@RestController
@PassToken
public class PictureController {

    @Value("${src.picture-path}")
    private String defaultPath;

    @Autowired
    private NonStaticResourceHttpRequestHandler requestHandler;
    static MultipartFile curFile;

    @PostMapping("/api/upload")
    public String upload() throws IOException {
        if (!curFile.isEmpty()) {
            //获取文件名
            String fileName = curFile.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 文件上传路径
            String filePath = System.getProperty("user.dir")
                    + "\\src\\main\\resources\\static\\files\\";
            // 解决中文路径,图片显示问题
            fileName = getFileNameNew() + suffixName;
            String url = filePath + fileName;
            File dest = new File(url);
            // 检测是否存在目录
            if (!dest.getParentFile().exists())
                dest.getParentFile().mkdirs();
            curFile.transferTo(dest);
            System.out.println("SAVE TO: " + url);
            return url;
        }
        System.err.println("not yet upload any file.");
        return "";
    }

    @RequestMapping("/api/getPhoto")
    public void getPhoto(@RequestParam MultipartFile file) throws IOException {
        curFile = file;
        System.out.println("UPLOAD: " + curFile.getOriginalFilename()
                + ", size of MB:" + curFile.getSize() / ((1 << 20) + 0.0));
        System.out.println(upload());
    }

    @GetMapping("/api/picture/{url}")
    public void photoPreview(HttpServletRequest request, HttpServletResponse response, @PathVariable String url) throws Exception {
        if (url.equals("null"))
            url = "default.png";
        if (!url.contains(defaultPath)) {
            url = defaultPath + url;
        }
        Path filePath = Paths.get(url);

        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (StringUtils.hasText(mimeType))
                response.setContentType(mimeType);
            request.setAttribute(ATTR_FILE, url);
            requestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }

    // 为文件重新命名，命名规则为当前系统时间毫秒数
    private static String getFileNameNew() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fmt.format(new Date());
    }

    // 以当前日期为名，创建新文件夹
    private static String createNewDir() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(new Date());
    }


}
