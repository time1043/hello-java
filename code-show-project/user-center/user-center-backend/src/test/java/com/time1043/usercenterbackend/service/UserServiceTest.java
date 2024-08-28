package com.time1043.usercenterbackend.service;

import com.time1043.usercenterbackend.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

/**
 * 用户服务测试类
 *
 * @author oswin
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    /**
     * 测试加密密码
     */
    @Test
    void testDigestPassword() {
        // spring 加密的工具类
        String newPassword = DigestUtils.md5DigestAsHex(("12345678" + "salt").getBytes());
        System.out.println(newPassword);  // c9d21e89dc04f9f2b446b4fbdafdf4b8
    }

    /**
     * 测试添加用户 (数据库操作层)
     */
    @Test
    void testAddUser() {
        // plugin: generate all setter
        User user = new User();

        //user.setId(0L);
        user.setUsername("zhangsan");
        user.setUserAccount("zhangsan501");
        user.setAvatarUrl("https://pic.616pic.com/ys_b_img/00/10/94/oGVCVUi1tc.jpg");
        user.setGender(0);
        user.setUserPassword("zhangsan123456");
        user.setPhone("13390905588");
        user.setEmail("zhangsan@qq.com");
        //user.setUserStatus(0);
        //user.setCreateTime(new Date());
        //user.setUpdateTime(new Date());
        //user.setIsDelete(0);
        //user.setUserRole(0);

        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    /**
     * 测试注册逻辑 (业务层)
     */
    @Test
    void testRegister() {
        String userAccount = "yingzhu2";
        String checkPassword = "123456";
        String planetCode = "nn00000009";

        // 非空检验
        String userPassword = "";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        // 账户长度不小于4
        userAccount = "yz";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        // 密码长度不小于8
        userAccount = "yingzhu2";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        // 账户不包含特殊字符
        userAccount = "yingzhu2#";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        // 账户名不能重复
        userAccount = "yingzhu";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        // 密码和校验密码不一致
        userAccount = "yingzhu2";
        userPassword = "12345678";
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        // planetCode TODO

        // 成功的
        userAccount = "yingzhu2";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertTrue(result > 0);
    }
}
