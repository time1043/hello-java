package com.time1043.usercenterbackend.common;

/**
 * 全局错误码
 *
 * @author oswin
 */
public enum ErrorCode {

    // 成功
    SUCCESS(0, "ok", ""),
    // 400 客户端错误
    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    NOT_LOGIN_ERROR(40100, "未登录", ""),
    NO_AUTH_ERROR(40101, "无权限", ""),
    // 500 服务端错误
    SYSTEM_ERROR(50000, "系统内部异常", "");


    private final int code;
    private final String message;  // 状态码信息
    private final String description;  // 详情描述

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
