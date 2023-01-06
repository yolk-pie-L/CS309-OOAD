package com.example.live_video.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.example.live_video.wrapper.NonStaticResourceHttpRequestHandler.ATTR_FILE;

public class FileUtil {

    public static boolean preview(String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Path filePath = Paths.get(url);

        if (Files.exists(filePath)) {
            String mimeType = Files.probeContentType(filePath);
            if (StringUtils.hasText(mimeType))
                response.setContentType(mimeType);
            request.setAttribute(ATTR_FILE, url);
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            return false;
        }
    }
}
