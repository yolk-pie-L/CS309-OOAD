package com.example.live_video.util;

import com.example.live_video.dto.UrlForm;
import com.example.live_video.wrapper.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

@ResponseResult
@RestController
public class Url {
    @PostMapping("/api/upload")
    public String upload(@RequestBody @Valid UrlForm urlForm) throws IOException {
        /*
        上传头像图片到静态资源文件夹中
         */
        System.out.println(urlForm);
        File file = urlForm.getFile();  // 这里仍然还是只能接收到null，解决这个就能用了
//        File file = new File("D:\\Pictures\\Wallpaper\\© bing\\1.jpg");
        if (file.exists()) {
            //获取文件名
            String fileName = file.getName();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 文件上传路径
            String filePath = System.getProperty("user.dir") + "\\CS309-OOAD\\LiveVideo\\src\\main\\resources\\static\\files\\";
            // 解决中文路径,图片显示问题
            fileName = UUID.randomUUID() + suffixName;
            String url = filePath + fileName;
            File dest = new File(url);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) dest.getParentFile().mkdirs();

            // 复制到本地
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(dest);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = fis.read(b)) != -1)
                fos.write(b);
            fis.close();
            fos.close();

            return url;
        }
        return "";
    }
}


//    @PostMapping("/api/upload1")
//    public String upload1(@RequestParam("file") MultipartFile file) {
//        /*
//        多文件
//         */
//        System.out.println(file);
//          MultipartFile file = new MockMultipartFile(
//                  "file", file_temp.getName(), null, Files.newInputStream(file_temp.toPath()));
//        if (!file.isEmpty()) {
//            //获取文件名
//            String fileName = file.getOriginalFilename();
//            // 获取文件的后缀名
//            String suffixName = fileName.substring(fileName.lastIndexOf("."));
//            // 文件上传路径
//            String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files\\";
//
//            // 解决中文路径,图片显示问题
//            fileName = UUID.randomUUID() + suffixName;
//            File dest = new File(filePath + fileName);
//            // 检测是否存在目录
//            if (!dest.getParentFile().exists()) {
//                dest.getParentFile().mkdirs();
//            }
//            try {
//                //上传
//                file.transferTo(dest);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return "/files/" + fileName;
//        }
//        return "redirect:/index";
//    }
//}
