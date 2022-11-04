package com.example.live_video.mapper;

import com.example.live_video.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback
public class UserMapperTest {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    public void testSelectAll(){
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
        System.out.println(userList.size());
    }
}
