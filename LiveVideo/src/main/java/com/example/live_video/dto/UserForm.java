package com.example.live_video.dto;

import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import lombok.Data;

@Data
public class UserForm {

    private String userName;

    private String password;

    private String repeatPassword;

    private String mail;

    private UserType userType;

    public boolean checkPasswordEquals(){
        return this.password.equals(this.repeatPassword);
    }

    public User convertToUser(){
        return new User(userName, userType, mail, password);
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                ", mail='" + mail + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
