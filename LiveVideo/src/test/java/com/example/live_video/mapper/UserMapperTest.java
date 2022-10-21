package com.example.live_video.mapper;

import com.example.live_video.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectAll(){
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
        System.out.println(userList.size());
    }
}
