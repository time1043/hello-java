package com.time1043.usercenterbackend;

import com.time1043.usercenterbackend.mapper.UserMapper;
import com.time1043.usercenterbackend.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class MybatisPlusSampleTest {
    @Resource
    private UserMapper userMapper;

    @Test  // org.junit.jupiter.api.Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }
}
