package com.example.live_video.service;

import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.MyException;
import com.example.live_video.exception.SQLUserNotFoundException;
import com.example.live_video.exception.SQLUsernameConflictException;
import com.example.live_video.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Test
    public void testGetUserId(){

    }

    @Test
    public void testRegister() {
        User user1 = new User("user1", UserType.Administrator, "user1@mail.com", "123456");
        boolean res1;
        boolean res2;
        try {
            res1 = userService.register(user1);
        }catch (MyException e){
            res1 = false;
        }
        try{
            res2 = userService.register(user1);
        }catch (MyException e){
            res2 = true;
        }
        assert res1;
        assert res2;
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
            res2 = true;
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
        try {
            boolean removeFlag = userService.removeUser(user1);
        } catch (MyException e) {
            e.printStackTrace();
        }
        long newCount = userService.count();
        assert newCount == oldCount - 1;
        userMapper.deleteById(user1);
    }
}
