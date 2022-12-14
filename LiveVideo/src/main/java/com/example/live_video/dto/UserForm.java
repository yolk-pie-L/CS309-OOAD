package com.example.live_video.dto;

import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class UserForm {
    @NotBlank(message = "Username is empty")
    private String userName;

    @Length(min = 6, message = "password should be at least 6 characters")
    private String password;

    private String repeatPassword;

    @Email
    private String mail;

    @Pattern(regexp = "^Student$|^Teacher$|^Administrator$")
    private String userType;

    private String photoUrl;

    private Long account;

    private String code;

    public boolean checkPasswordEquals(){
        return this.password.equals(this.repeatPassword);
    }

    public User convertToUser(){
        if (account != null)
            return new User(userName, UserType.valueOf(userType), mail, password, photoUrl, account);
        else
            return new User(userName, UserType.valueOf(userType), mail, password);
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                ", mail='" + mail + '\'' +
                ", code='" + code + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
