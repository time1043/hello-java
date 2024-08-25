package com.time1043.usercenterbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.time1043.usercenterbackend.model.domain.User;
import com.time1043.usercenterbackend.model.domain.request.UserLoginRequest;
import com.time1043.usercenterbackend.model.domain.request.UserRegisterRequest;
import com.time1043.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.time1043.usercenterbackend.contant.UserConstant.ADMIN_ROLE;
import static com.time1043.usercenterbackend.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author oswin
 */
@RestController  // restful风格api  返回值默认json
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }

        // plugin: auto filling java call arguments
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        // 粗略校验 (controller层对请求参数的校验 没有涉及业务逻辑)
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public User userLogin(
            @RequestBody UserLoginRequest userLoginRequest,
            HttpServletRequest request
    ) {
        if (userLoginRequest == null) {
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.userLogin(userAccount, userPassword, request);
    }

    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request) {
        // 从请求中拿到登录态 cookie
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            return null;
        }

        // 数据库查询 (如果有积分信息)
        Long userId = currentUser.getId();
        User user = userService.getById(userId);
        // TODO 用户状态可能处于封号状态
        return userService.getSafetyUser(user);  // 脱敏
    }

    @GetMapping("/search")
    public List<User> searchUser(String username, HttpServletRequest request) {
        // 非管理员禁止搜索
        if (!isAdmin(request)) {
            return new ArrayList<>();
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(
                user -> userService.getSafetyUser(user)
        ).collect(Collectors.toList());  // 脱敏
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest request) {
        // 非管理员禁止删除
        if (!isAdmin(request)) {
            return false;
        }
        if (id <= 0) {
            return false;
        }

        return userService.removeById(id);  // mybatis-plus BaseMapper
    }

    /**
     * 鉴权：是否是管理员
     *
     * @param request 请求
     * @return 是否是管理员
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        return currentUser != null && currentUser.getUserRole() == ADMIN_ROLE;
    }
}
