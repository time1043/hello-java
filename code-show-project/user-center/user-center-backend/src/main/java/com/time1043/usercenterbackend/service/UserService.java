package com.time1043.usercenterbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.time1043.usercenterbackend.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author oswin
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2024-08-25 13:56:11
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @param checkPassword 确认密码
     * @param planetCode    星球编码 (用户校验)
     * @return 注册成功返回用户id，失败返回-1
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request      http请求
     * @return 返回脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request http请求
     * @return 返回1表示注销成功
     */
    int userLogout(HttpServletRequest request);

    /**
     * 获取脱敏的用户信息 (工具类)
     *
     * @param originalUser 原始用户信息
     * @return 脱敏后的用户信息
     */
    User getSafetyUser(User originalUser);
}
