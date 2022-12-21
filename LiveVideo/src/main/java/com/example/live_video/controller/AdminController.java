package com.example.live_video.controller;

import com.example.live_video.wrapper.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@ResponseResult
@RestController
public class AdminController {

    @PostMapping("/api/privilege")
    public Boolean updatePrivilege(@RequestParam String administrator,
                                   @RequestParam String userName,
                                   @RequestParam String privilegeType) {
        // TODO: adminService Completed
        return null;
    }
}
