package com.example.live_video.dto;

import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class UserForm {
    @NotBlank(message = "Username is empty")
    private String userName;

    @Length(min = 6, message = "password should be at least 6 characters")
    private String password;

    private String repeatPassword;
    private String mail;

    private UserType userType;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getMail() { return mail; }
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
