package com.example.live_video.service;

import com.example.live_video.entity.User;
import com.example.live_video.exception.SQLUserNotFoundException;
import com.example.live_video.exception.SQLUsernameConflictException;
import com.example.live_video.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Test
    public void testRegister(){
        User user1 = new User("user1", 'A', "user1@mail.com", "123456", null, 0L);
        boolean res1;
        boolean res2;
        try {
            res1 = userService.register(user1);
        }catch (SQLUsernameConflictException e){
            res1 = false;
        }
        try{
            res2 = userService.register(user1);
        }catch (SQLUsernameConflictException e){
            res2 = true;
        }
        assert res1;
        assert res2;
        userService.removeById(user1);
    }

    @Test
    public void testLogin(){
        User user1 = new User("user1", 'A', "user1@mail.com", "123456", null, 0L);
        User user2 = new User("notexist", 'A', "notexist@mail.com", "123", null, 0L);
        User user3 = new User("user1", 'A', "user1@mail.com", "password", null, 0L);
        userMapper.insert(user1);
        boolean res1;
        boolean res2;
        boolean res3;
        try {
            res1 = userService.compareUserPassword(user1);
        }catch (SQLUserNotFoundException e){
            res1 = false;
        }
        try{
            userService.compareUserPassword(user2);
            res2 = false;
        }catch (SQLUserNotFoundException e){
            res2 = true;
        }
        try{
            res3 = userService.compareUserPassword(user3);
        }catch (SQLUserNotFoundException e){
            res3 = true;
        }
        assert res1;
        assert res2;
        assert !res3;
        userMapper.deleteById(user1);
    }

    @Test
    public void TestRemoveUser(){
        User user1 = new User("user1", 'A', "user1@mail.com", "123456", null, 0L);
        userMapper.insert(user1);
        long oldCount = userService.count();
        boolean removeFlag = userService.removeUser(user1);
        long newCount = userService.count();
        assert removeFlag;
        assert newCount == oldCount - 1;
        userMapper.deleteById(user1);
    }
}
