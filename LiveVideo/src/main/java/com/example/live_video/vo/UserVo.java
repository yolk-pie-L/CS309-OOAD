package com.example.live_video.vo;

import com.example.live_video.entity.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserVo {

    private String userName;

    private String userType;

    private String mail;

    private String photoUrl;

    private Long account;

    public UserVo(String userName, String userType, String mail, String photoUrl, Long account) {
        this.userName = userName;
        this.userType = userType;
        this.mail = mail;
        this.photoUrl = photoUrl;
        this.account = account;
    }

    public static UserVo parse(User user) {
        return new UserVo(
                user.getUserName(),
                user.getUserType().toString(),
                user.getMail(),
                user.getPhotoUrl(),
                user.getAccount());
    }

    public static List<UserVo> parse(List<User> userList){
        List<UserVo> userVoList = new ArrayList<>();
        userList.forEach(user -> userVoList.add(UserVo.parse(user)));
        return userVoList;
    }
}
