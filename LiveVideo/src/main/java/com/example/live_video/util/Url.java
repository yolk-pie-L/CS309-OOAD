package com.example.live_video.util;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

public class Url {
    @PostMapping("/upload")
    public String upload(@RequestParam("filename") MultipartFile file) {
        /*
        上传头像图片到静态资源文件夹中
         */
        if (!file.isEmpty()) {
            //获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 文件上传路径
            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files\\";

            // 解决中文路径,图片显示问题
            fileName = UUID.randomUUID() + suffixName;
            File dest = new File(filePath + fileName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                //上传
                file.transferTo(dest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "/files/" + fileName;
        }
        return "redirect:/index";
    }
}
