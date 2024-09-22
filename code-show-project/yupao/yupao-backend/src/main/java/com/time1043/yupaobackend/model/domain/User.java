package com.time1043.yupaobackend.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 用户
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    /**
     * 唯一ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录账号
     */
    private String userAccount;

    /**
     * 用户头像URL
     */
    private String avatarUrl;

    /**
     * 用户性别
     */
    private Integer gender;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户电话号码
     */
    private String phone;

    /**
     * 用户电子邮件
     */
    private String email;

    /**
     * 用户状态0为正常
     */
    private Integer userStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除标志
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 用户角色 0是普通用户 1是管理员
     */
    private Integer userRole;

    /**
     * 星球编号
     */
    private String planetCode;

    /**
     * 用户标签 json
     */
    private String tags;

    /**
     * 用户简介
     */
    private String profile;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}