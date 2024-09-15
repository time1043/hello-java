package com.time1043.yupaobackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.time1043.yupaobackend.common.ErrorCode;
import com.time1043.yupaobackend.exception.BusinessException;
import com.time1043.yupaobackend.mapper.UserMapper;
import com.time1043.yupaobackend.model.domain.User;
import com.time1043.yupaobackend.service.UserService;
import com.time1043.yupaobackend.utils.LogExecutionTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.time1043.yupaobackend.contant.UserConstant.USER_LOGIN_STATE;

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
        safetyUser.setTags(originalUser.getTags());
        return safetyUser;
    }


    /**
     * 根据标签搜索用户 (SQL)
     *
     * @param tagNameList 用户要拥有的标签列表
     * @return 用户列表
     */
    @LogExecutionTime
    @Override
    public List<User> searchUsersByTagsBySQL(List<String> tagNameList) {
        // 不允许：用户不输入条件，返回全部用户
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标签不能为空");
        }

        // #################################################
        // WAY1: SQL
        // #################################################
        // 拼接 and 查询  // queryWrapper.like("column", "value").like("column", "value")
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        for (String tagName : tagNameList) {
            queryWrapper = queryWrapper.like("tags", tagName);
        }
        List<User> userList = userMapper.selectList(queryWrapper);

        // userList.forEach(user -> {getSafetyUser(user);});
        // userList.forEach(this::getSafetyUser);  // Java8 lambda
        return userList.stream().map(this::getSafetyUser).collect(Collectors.toList());  // Java8 stream
    }

    /**
     * 根据标签搜索用户 (Memory)
     *
     * @param tagNameList 用户要拥有的标签列表
     * @return 用户列表
     */
    @LogExecutionTime
    @Override
    public List<User> searchUsersByTagsByMemory(List<String> tagNameList) {
        // 不允许：用户不输入条件，返回全部用户
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标签不能为空");
        }

        // #################################################
        // WAY2: Memory
        // #################################################
        // SQL 所有用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper);

        // Memory 判断符合要求 (灵活)
        Gson gson = new Gson();
        /*for (User user : userList) {
            String tagsStr = user.getTags();  // str -> json: List<User>
            Set<String> tempTagNameSet = gson.fromJson(tagsStr, new TypeToken<Set<String>>() {
            }.getType());  // O(1)
            // gson.toJson(tempTagNameList);  // json -> str

            for (String tagName : tagNameList) {
                if (!tempTagNameSet.contains(tagName)) {
                    return null;
                }
            }
        }*/
        return userList.stream().filter(user -> {
            String tagsStr = user.getTags();

            // 数据库中没有符合要求的用户
            if (StringUtils.isBlank(tagsStr)) {
                return false;
            }

            Set<String> tempTagNameSet = gson.fromJson(tagsStr, new TypeToken<Set<String>>() {
            }.getType());
            tempTagNameSet = Optional.ofNullable(tempTagNameSet).orElse(new HashSet<>());  // db中tags字段可能为null  java8 Optional
            for (String tagName : tagNameList) {
                if (!tempTagNameSet.contains(tagName)) {
                    return false;
                }
            }
            return true;
        }).map(this::getSafetyUser).collect(Collectors.toList());  // Java8 stream parallelStream  // 公共线程池 有未知风险
    }

}

