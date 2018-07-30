package com.web.cloud.common.constant;

public enum LoginType {

    /**
     * 后台管理员登录
     */
    ADMIN(1),

    /**
     * 用户登录
     */
    USER(2),

    /**
     * 企业管理员登录
     */
    ENTERPRISE(3),
    ;

    private int value;

    LoginType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
