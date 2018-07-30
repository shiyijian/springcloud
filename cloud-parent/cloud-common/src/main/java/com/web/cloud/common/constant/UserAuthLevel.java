package com.web.cloud.common.constant;

public enum UserAuthLevel {
    LEVEL_GUEST(1, "未登录状态的游客"),
    LEVEL_USER(2, "已登录用户"),
    LEVEL_ADMIN(3, "后台管理员");

    private int value;
    private String comment;

    private UserAuthLevel(int value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public int getValue() {
        return value;
    }
}
