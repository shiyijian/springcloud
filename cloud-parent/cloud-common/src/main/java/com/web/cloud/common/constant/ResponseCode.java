package com.web.cloud.common.constant;

public enum ResponseCode {

    RESPONSE_SUCCESS(10000, "success"),

    //业务级错误
    PARAM_E_PARAM_MISSING(20002, "the param is missing"),
    P_E_API_SIGN_ERROR(20007, "api签名无效"),
    P_E_PARAM_ISNULL(20013, "参数为空"),
    B_E_APP_KEY_INVALID(30013, "appKey无效"),
    B_E_AUTH_LOGIN_LIMIT(30020,"接口权限限制"),

    /**
     * 系统内部错误
     */
    SYS_E_DEFAULT_ERROR(40001, "%s 系统开小差中，请稍后再试"),
    SYS_E_SERVICE_EXCEPTION(40002, "服务端异常"),
    SYS_E_DATABASE_ERROR(40003, "数据库操作异常"),
    SYS_E_REMOTE_CALL_ERROR(40004, "remote call error"),

    /**
     * 状态错误
     */
    P_E_SESSION_TOKEN_MISSING(50001, "参数缺少session_token"),
    P_E_SESSION_TOKEN_INVALID(50002, "session_token无效或已过期"),
    P_E_ACCESS_TOKEN_MISSING(50003, "参数缺少access_token"),
    P_E_ACCESS_TOKEN_INVALID(50004, "access_token无效或已过期"),
    P_E_REFRESH_TOKEN_MISSING(50005, "参数缺少refresh_token"),
    P_E_REFRESH_TOKEN_INVALID(50006, "refresh_token无效或已过期"),

    ;
    private int code;
    private String comment;

    private ResponseCode(int code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public int getCode() {
        return this.code;
    }

    public String getComment() {
        return this.comment;
    }
}
