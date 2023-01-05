package com.example.live_video.service;

import com.example.live_video.entity.AdminRight;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.PermissionDeniedException;
import com.example.live_video.mapper.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @Autowired
    UserMapper userMapper;

    List<User> userList = new ArrayList<>();

    Long count;

    @Test
    void tt(){
        String defaultUrlPath = System.getProperty("user.dir") + File.separator + "src/main/resources/static/picture/default.png";
        System.out.println(defaultUrlPath);
    }

    @BeforeEach
    void setUp() {
        count = userMapper.selectCount(null);
        User user2 = new User("user2", UserType.Teacher, "user2@mail.com1", "123456");
        User user3 = new User("2user3", UserType.Student, "user3@mail.com1", "123456");
        User user4 = new User("2user4", UserType.Student, "user4@mail.com1", "123456");
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.forEach(u -> userMapper.insert(u));
    }

    @AfterEach
    void tearDown() {
        userList.forEach(u -> userMapper.deleteById(u));
    }


    @Test
    void addOrRemoveAdmin() {
    }

    @Test
    void testGetUserList() {
        List<User> users = adminService.getUserList();
        assert users.size() == count + 3;
        int t_count = 0;
        int s_count = 0;
        for(User u: users){
            assert u.getAdminRight() == AdminRight.NonAdmin ||u.getAdminRight() == AdminRight.SuperAdmin;
            if(u.getUserType().equals(UserType.Teacher))
                t_count++;
            else if(u.getUserType().equals(UserType.Student))
                s_count++;
        }
        assert s_count==2;
        assert t_count==1;
        users = adminService.getUserList("2user");
        assert users.get(0).getUserType().equals(UserType.Student);
        assert users.get(1).getUserType().equals(UserType.Student);
        assert users.size() == 2;
        boolean flag = false;
        try {
            flag = adminService.addOrRemoveAdmin("lixin", users.get(0).getUserName());
        }catch (PermissionDeniedException e){
            assert false;
        }
        assert flag;
        List<User> users1 = adminService.getUserList(users.get(0).getUserName());
        User user1 = users1.get(0);
        assert user1.getUserName().equals(users.get(0).getUserName());
        assert user1.getAdminRight().equals(AdminRight.Admin);
        try{
            adminService.addOrRemoveAdmin(user1.getUserName(), "lixin");
            assert false;
        }catch (PermissionDeniedException e){
            assert true;
        }
        try{
            adminService.addOrRemoveAdmin(user1.getUserName(), "user2");
            assert true;
        }catch (PermissionDeniedException e){
            assert false;
        }
        users = adminService.getUserList();
        long count1 = 0;
        for(User u: users){
            assert !u.getUserName().equals("user2") || u.getAdminRight().equals(AdminRight.Admin);
            if(u.getAdminRight().equals(AdminRight.NonAdmin)){
                count1++;
            }
        }
        assert count1 == 1;
        User user2 = userList.get(0);
        try {
            adminService.addOrRemoveAdmin(user1.getUserName(), user2.getUserName());
        }catch (PermissionDeniedException e){
            assert false;
        }
        users = adminService.getUserList();
        for(User u: users){
            assert !u.getUserName().equals(user2.getUserName()) || u.getAdminRight() == AdminRight.NonAdmin;
        }
    }

    @Test
    void testGetUserList1() {
    }
}