package com.example.live_video.dto;

import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class UserForm {
    @NotBlank(message = "Username is empty")
    private String userName;

    @Length(min = 6, message = "password should be at least 6 characters")
    private String password;

    private String repeatPassword;

    private String mail;

    private UserType userType;

    private String photoUrl;

    private Long account;

    public boolean checkPasswordEquals(){
        return this.password.equals(this.repeatPassword);
    }

    public User convertToUser(){
        if (account != null)
            return new User(userName, userType, mail, password, photoUrl, account);
        else
            return new User(userName, userType, mail, password);
    }
}
