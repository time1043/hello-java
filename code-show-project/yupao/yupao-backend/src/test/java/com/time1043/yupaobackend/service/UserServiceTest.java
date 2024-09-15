package com.time1043.yupaobackend.service;

import com.time1043.yupaobackend.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

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

    /**
     * 测试根据标签搜索用户 (业务层 - SQL)
     */
    @Test
    void searchUsersByTagsBySQL() {
        List<String> tagNameList = Arrays.asList("python", "javascript");
        List<User> userList = userService.searchUsersByTagsBySQL(tagNameList);
        Assert.assertNotNull(userList);

        /*
        ==>  Preparing: SELECT id,username,userAccount,avatarUrl,gender,userPassword,phone,email,userStatus,createTime,updateTime,isDelete,userRole,planetCode,tags FROM user WHERE isDelete=0 AND (tags LIKE ? AND tags LIKE ?)
        ==> Parameters: %python%(String), %javascript%(String)
        <==    Columns: id, username, userAccount, avatarUrl, gender, userPassword, phone, email, userStatus, createTime, updateTime, isDelete, userRole, planetCode, tags
        <==        Row: 2, yupi, yupi501, https://miro.medium.com/v2/resize:fit:640/format:webp/0*1Og_hmJWdlMiDWuB.png, 1, c9d21e89dc04f9f2b446b4fbdafdf4b8, 15534340089, yupi501@gmail.com, 0, 2024-09-15 16:55:56, 2024-09-15 16:55:56, 0, 0, nn00000002, ["python", "javascript"]
        <==        Row: 4, yupi3, yupi503, https://miro.medium.com/v2/resize:fit:640/format:webp/0*1Og_hmJWdlMiDWuB.png, 1, c9d21e89dc04f9f2b446b4fbdafdf4b8, 15534340089, yupi501@gmail.com, 1, 2024-09-15 16:55:56, 2024-09-15 16:55:56, 0, 0, nn00000004, ["python", "javascript"]
        <==      Total: 2
        */
    }

    @Test
    void searchUsersByTagsByMemory() {
        List<String> tagNameList = Arrays.asList("python", "javascript");
        List<User> userList = userService.searchUsersByTagsByMemory(tagNameList);
        Assert.assertNotNull(userList);

        /*
        ==>  Preparing: SELECT id,username,userAccount,avatarUrl,gender,userPassword,phone,email,userStatus,createTime,updateTime,isDelete,userRole,planetCode,tags FROM user WHERE isDelete=0
        ==> Parameters:
        <==    Columns: id, username, userAccount, avatarUrl, gender, userPassword, phone, email, userStatus, createTime, updateTime, isDelete, userRole, planetCode, tags
        <==        Row: 1, oswin, oswin501, https://miro.medium.com/v2/resize:fit:640/format:webp/1*4j2A9niz0eq-mRaCPUffpg.png, 1, c9d21e89dc04f9f2b446b4fbdafdf4b8, 15534340089, oswin501@gmail.com, 0, 2024-09-15 16:55:56, 2024-09-15 16:55:56, 0, 1, nn00000001, ["java", "cpp"]
        <==        Row: 2, yupi, yupi501, https://miro.medium.com/v2/resize:fit:640/format:webp/0*1Og_hmJWdlMiDWuB.png, 1, c9d21e89dc04f9f2b446b4fbdafdf4b8, 15534340089, yupi501@gmail.com, 0, 2024-09-15 16:55:56, 2024-09-15 16:55:56, 0, 0, nn00000002, ["python", "javascript"]
        <==        Row: 4, yupi3, yupi503, https://miro.medium.com/v2/resize:fit:640/format:webp/0*1Og_hmJWdlMiDWuB.png, 1, c9d21e89dc04f9f2b446b4fbdafdf4b8, 15534340089, yupi501@gmail.com, 1, 2024-09-15 16:55:56, 2024-09-15 16:55:56, 0, 0, nn00000004, ["python", "javascript"]
        <==      Total: 3
         */
    }

    @Test
    void searchUsersByTagsTime() {
        List<String> tagNameList = Arrays.asList("python", "javascript");

        List<User> userList = userService.searchUsersByTagsBySQL(tagNameList);  // 数据库连接时间
        List<User> userList1 = userService.searchUsersByTagsBySQL(tagNameList);
        List<User> userList2 = userService.searchUsersByTagsByMemory(tagNameList);

        /*
        // 数据库连接时间  // 少量数据时，SQL查询更快   // 多量数据 1w
        List com.time1043.yupaobackend.service.impl.UserServiceImpl.searchUsersByTagsBySQL(List) executed in 236 ms
        List com.time1043.yupaobackend.service.impl.UserServiceImpl.searchUsersByTagsBySQL(List) executed in 3 ms  // 分治
        List com.time1043.yupaobackend.service.impl.UserServiceImpl.searchUsersByTagsByMemory(List) executed in 16 ms  // 并发 多线程
         */
    }
}
