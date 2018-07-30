package com.web.cloud.feign.response;

import com.web.cloud.common.constant.ResponseCode;

public class MopResponse<T> {
    private int code;
    private String msg;
    private T data;
    private Throwable exception;

    public MopResponse(T data){
        this.code = ResponseCode.RESPONSE_SUCCESS.getCode();
        this.msg = ResponseCode.RESPONSE_SUCCESS.getComment();
        this.data = data;
    }

    public MopResponse(ResponseCode responseEnum, String message){
        this.code = responseEnum.getCode();
        this.msg = message;
    }

    public MopResponse(ResponseCode responseEnum){
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getComment();
    }

    public MopResponse(int code, String msg){
        this.code = code;
        this.msg = msg;
    }



    public MopResponse(Throwable exception){
        this.code = ResponseCode.SYS_E_DEFAULT_ERROR.getCode();
        this.msg = ResponseCode.SYS_E_DEFAULT_ERROR.getComment();
        this.exception = exception;
    }

    public boolean isSuccess(){
        return this.code == ResponseCode.RESPONSE_SUCCESS.getCode();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public Throwable getException() {
        return exception;
    }
}
