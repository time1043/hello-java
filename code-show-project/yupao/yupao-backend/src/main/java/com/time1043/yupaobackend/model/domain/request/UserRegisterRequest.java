package com.time1043.yupaobackend.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求参数类
 *
 * @author oswin
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -5828182211292243190L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;
}
