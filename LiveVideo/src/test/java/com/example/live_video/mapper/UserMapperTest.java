package com.example.live_video.mapper;

import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
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
        User user = new User("uuu", UserType.Student, "120@", "12", "assign_url", 1L);
        userMapper.insert(user);
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
        System.out.println(userList.size());
        userMapper.deleteById(user);
    }
}
