package com.time1043.usercenterbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.time1043.usercenterbackend.common.BaseResponse;
import com.time1043.usercenterbackend.common.ErrorCode;
import com.time1043.usercenterbackend.common.ResultUtils;
import com.time1043.usercenterbackend.exception.BusinessException;
import com.time1043.usercenterbackend.model.domain.User;
import com.time1043.usercenterbackend.model.domain.request.UserLoginRequest;
import com.time1043.usercenterbackend.model.domain.request.UserRegisterRequest;
import com.time1043.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            // return ResultUtils.error(ErrorCode.PARAMS_ERROR);  // way1: ugly (通用返回对象 + 自定义错误码)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");  // way2: better (+ 封装异常类)
        }

        // plugin: auto filling java call arguments
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();

        // 粗略校验 (controller层对请求参数的校验 没有涉及业务逻辑)
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户、密码、确认密码、星球编号不能为空");
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);  // user id

        // return new BaseResponse<>(0, result, "ok");  // way1: ugly (通用返回对象)
        return ResultUtils.success(result);  // way2: better (+ 返回对象工具类)
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(
            @RequestBody UserLoginRequest userLoginRequest,
            HttpServletRequest request
    ) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码不能为空");
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }

        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        // 从请求中拿到登录态 cookie
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
        }

        // 数据库查询 (如果有积分信息)
        Long userId = currentUser.getId();
        User user = userService.getById(userId);
        // TODO 用户状态可能处于封号状态
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String username, HttpServletRequest request) {
        // 非管理员禁止搜索
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "非管理员禁止搜索");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> safetyUserList = userList.stream().map(
                user -> userService.getSafetyUser(user)
        ).collect(Collectors.toList());
        return ResultUtils.success(safetyUserList);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        // 非管理员禁止删除
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "非管理员禁止删除");
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "要删除用户的id错误");
        }

        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
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
