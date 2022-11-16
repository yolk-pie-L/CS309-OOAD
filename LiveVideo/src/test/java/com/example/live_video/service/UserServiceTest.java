package com.example.live_video.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.SQLMailConflictException;
import com.example.live_video.exception.SQLUserNotFoundException;
import com.example.live_video.exception.SQLUsernameConflictException;
import com.example.live_video.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@Transactional
@Rollback
public class UserServiceTest {

    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired
    UserService userService;

    List<User> allUsers = new ArrayList<>();

    void setUp(){
        User user1 = new User("user1", UserType.Administrator, "user1@mail.com", "123456");
        User user2 = new User("user2", UserType.Teacher, "user2@mail.com1", "123456");
        User user3 = new User("user3", UserType.Student, "user3@mail.com1", "123456");
        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user2);
        userMapper.insert(user1);
        userMapper.insert(user2);
        userMapper.insert(user3);
    }

    void tearDown(){
        userMapper.deleteBatchIds(allUsers);
    }

    @Test
    public void testRegister(){
        User user1 = new User("user1", UserType.Administrator, "user1@mail.com", "123456");
        User user2 = new User("user1", UserType.Administrator, "user1@mail.com1", "123456");
        User user3 = new User("user3", UserType.Administrator,"user1@mail.com", "123456");
        boolean res1 = false;
        boolean res2 = false;
        boolean res3 = false;
        res1 = (boolean) userService.register(user1);
        res2 = userService.register(user2) instanceof SQLUsernameConflictException;
        res3 = userService.register(user3) instanceof SQLMailConflictException;
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
        Object res2;
        boolean res3;
        res1 = (boolean) userService.compareUserPassword(user1);
        res2 = userService.compareUserPassword(user2);
        res3 = (boolean) userService.compareUserPassword(user3);
        assert res1;
        assert res2 instanceof SQLUserNotFoundException;
        assert !res3;
        userMapper.deleteById(user1);
    }

    @Test
    public void TestRemoveUser(){
        User user1 = new User("user1", UserType.Administrator, "user1@mail.com", "123456");
        userMapper.insert(user1);
        long oldCount = userService.count();
        boolean removeFlag;
        removeFlag = (boolean) userService.removeUser(user1.getUserName());
        long newCount = userService.count();
        assert removeFlag;
        assert newCount == oldCount - 1;
        userMapper.deleteById(user1);
        removeFlag = userService.removeUser(user1.getUserName()) instanceof SQLUserNotFoundException;
        assert removeFlag;
    }

    @Test
    void getUserTypeByUsername(){
        setUp();
        for(User user: allUsers){
            UserType userType = userService.getUserTypeByUsername(user.getUserName());
            assert userType == user.getUserType();
        }
        tearDown();
    }

    @Test
    void getUserIdByUsername(){
        setUp();
        for (User user : allUsers) {
            Long id = userService.getUserIdByUsername(user.getUserName());
            assert Objects.equals(id, user.getId());
        }
        tearDown();
    }

    @Test
    void getUserAccountByUsername() {
        User user1 = new User("user1", UserType.Administrator, "user1@mail.com", "123456");
        user1.setAccount(10L);
        userMapper.insert(user1);
        Long account = userService.getUserAccountByUsername(user1.getUserName());
        assert account.equals(10L);
        userMapper.deleteById(user1);
    }

    @Test
    void updateUser() {
        User user1 = new User("user1", UserType.Administrator, "user1@mail.com", "123456");
        user1.setAccount(10L);
        userMapper.insert(user1);
        User user2 = new User("user1", null, null, "12336", "url", 1L);
        userService.updateUser(user2);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", user2.getUserName());
        User res = userMapper.selectOne(userQueryWrapper);
        System.out.println(res);
        assert res.getMail().equals(user1.getMail());
        assert res.getUserType().equals(user1.getUserType());
        assert res.getPhotoUrl().equals(user2.getPhotoUrl());
        assert res.getAccount().equals(user2.getAccount());
    }
}
