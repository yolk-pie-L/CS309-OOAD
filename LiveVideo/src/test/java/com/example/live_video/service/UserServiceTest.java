package com.example.live_video.service;

import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.*;
import com.example.live_video.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Test
    public void testRegister(){
        User user1 = new User("user1", UserType.Administrator, "user1@mail.com", "123456");
        User user2 = new User("user1", UserType.Administrator, "user1@mail.com1", "123456");
        User user3 = new User("user3", UserType.Administrator,"user1@mail.com", "123456");
        boolean res1 = false;
        boolean res2 = false;
        boolean res3 = false;
        try {
            res1 = userService.register(user1);
        }catch (MyException e){
            res1 = false;
        }
        try{
            res2 = userService.register(user2);
        }catch (MyException e){
            if(e instanceof SQLUsernameConflictException)
                res2 = true;
        }
        try{
            res3 = userService.register(user3);
        }catch (MyException e){
            if(e instanceof SQLUsernameConflictException)
                res3 = false;
            else if(e instanceof SQLMailConflictException)
                res3 = true;
        }
        assert res1;
        assert res2;
        assert res3;
        userService.removeById(user1);
    }

    @Test
    public void testLogin(){
        User user1 = new User("user1", UserType.Administrator, "user1@mail.com", "123456");
        User user2 = new User("notexist", UserType.Administrator, "notexist@mail.com", "123");
        User user3 = new User("user1", UserType.Administrator, "user1@mail.com", "password");
        userMapper.insert(user1);
        boolean res1;
        boolean res2;
        boolean res3;
        try {
            res1 = userService.compareUserPassword(user1);
        }catch (MyException e){
            res1 = false;
        }
        try{
            userService.compareUserPassword(user2);
            res2 = false;
        }catch (MyException e){
            res2 = e instanceof SQLUserNotFoundException;
        }
        try{
            res3 = userService.compareUserPassword(user3);
        }catch (MyException e){
            res3 = true;
        }
        assert res1;
        assert res2;
        assert !res3;
        userMapper.deleteById(user1);
    }

    @Test
    public void TestRemoveUser(){
        User user1 = new User("user1", UserType.Administrator, "user1@mail.com", "123456");
        userMapper.insert(user1);
        long oldCount = userService.count();
        boolean removeFlag;
        try {
            removeFlag = userService.removeUser(user1);
        }catch (MyException e){
            removeFlag = false;
        }
        long newCount = userService.count();
        assert removeFlag;
        assert newCount == oldCount - 1;
        userMapper.deleteById(user1);
        try{
            removeFlag = userService.removeUser(user1);
        }catch (MyException e){
            removeFlag = e instanceof SQLUserNotFoundException;
        }
        assert removeFlag;
    }
}
