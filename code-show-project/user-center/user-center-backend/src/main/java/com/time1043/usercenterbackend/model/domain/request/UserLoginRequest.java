package com.time1043.usercenterbackend.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求参数类
 *
 * @author oswin
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -6452848799120833655L;

    private String userAccount;
    private String userPassword;
}
