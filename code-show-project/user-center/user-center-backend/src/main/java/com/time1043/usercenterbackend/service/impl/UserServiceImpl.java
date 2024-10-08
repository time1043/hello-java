package com.time1043.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.time1043.usercenterbackend.common.ErrorCode;
import com.time1043.usercenterbackend.exception.BusinessException;
import com.time1043.usercenterbackend.mapper.UserMapper;
import com.time1043.usercenterbackend.model.domain.User;
import com.time1043.usercenterbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.time1043.usercenterbackend.contant.UserConstant.USER_LOGIN_STATE;

/**
 * @author oswin
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-08-25 13:56:11
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    /**
     * 注入UserMapper
     */
    @Resource
    private UserMapper userMapper;

    /**
     * 密码加盐
     */
    private static final String SALT = "salt";

    /**
     * 用户注册
     *
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @param checkPassword 确认密码
     * @param planetCode    星球编码 (用户校验)
     * @return 用户id 注册失败返回-1
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // #################################################
        // 校验
        // #################################################
        // 空字符串 位数过少
        if (StringUtils.isAllBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名长度不能少于4位");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不能少于8位");
        }
        if (planetCode.length() > 12) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编码长度不能超过12位");
        }
        // 账户不能包含特殊字符 正则
        String validPattern = "^[a-zA-Z0-9]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名不能包含特殊字符");
        }
        // 密码和确认密码一致
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码和确认密码不一致");
        }
        // 账户不能重复 数据库查询 (性能消耗的放后面!!)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        Long count = userMapper.selectCount(queryWrapper);  // this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名已存在");
        }
        // 星球编号不能重复 数据库查询
        queryWrapper.clear();
        queryWrapper.eq("planetCode", planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号已存在");
        }

        // #################################################
        // 加密
        // #################################################
        String encryptPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());

        // #################################################
        // 插入数据库
        // #################################################
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        user.setUsername("username");
        user.setAvatarUrl("https://miro.medium.com/v2/resize:fit:640/format:webp/0*1Og_hmJWdlMiDWuB.png");
        boolean saveResult = this.save(user);  // this.  service.
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return user.getId();
    }

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request      http请求
     * @return 登录成功返回用户对象，登录失败返回null
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // #################################################
        // 校验
        // #################################################
        // 空字符串 位数过少
        if (StringUtils.isAllBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名长度不能少于4位");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不能少于8位");
        }
        // 账户不能包含特殊字符 正则
        String validPattern = "^[a-zA-Z0-9]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名不能包含特殊字符");
        }

        // #################################################
        // 加密
        // #################################################
        String encryptPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());

        // #################################################
        // 查询数据库 (查不到逻辑逻辑删除的)
        // #################################################
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户名或密码错误 or 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount: {}, userPassword: {}", userAccount, userPassword);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码错误");
        }
        // TODO 限流策略

        // #################################################
        // 放行登陆
        // #################################################
        // 脱敏处理
        User safetyUser = getSafetyUser(user);
        // 记录用户登录态 session cookie
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);  // k v
        return safetyUser;
    }

    /**
     * 用户注销
     *
     * @param request http请求
     * @return 返回1表示注销成功
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        // 移出登陆态 session cookie
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    /**
     * 获取脱敏用户信息 (工具类)
     *
     * @param originalUser 原始用户信息
     * @return 脱敏后的用户信息
     */
    @Override
    public User getSafetyUser(User originalUser) {
        // 空处理
        if (originalUser == null) {
            return null;
        }

        User safetyUser = new User();
        safetyUser.setId(originalUser.getId());
        safetyUser.setUsername(originalUser.getUsername());
        safetyUser.setUserAccount(originalUser.getUserAccount());
        safetyUser.setAvatarUrl(originalUser.getAvatarUrl());
        safetyUser.setGender(originalUser.getGender());
        safetyUser.setPhone(originalUser.getPhone());
        safetyUser.setEmail(originalUser.getEmail());
        safetyUser.setUserRole(originalUser.getUserRole());
        safetyUser.setPlanetCode(originalUser.getPlanetCode());
        safetyUser.setUserStatus(originalUser.getUserStatus());
        safetyUser.setCreateTime(originalUser.getCreateTime());
        return safetyUser;
    }
}
