package com.web.cloud.common.response.impl;

import com.web.cloud.common.constant.ResponseCode;
import com.web.cloud.common.response.Response;

import java.io.Serializable;

public class BaseResponse<T> implements Response,Serializable {

    private T module;
    private int code;
    private String message;
    private long totalCount = 0;

    public BaseResponse() {
    }

    public BaseResponse(ResponseCode responseCode, String message){
        this.code = responseCode.getCode();
        this.message = message;
    }

    public BaseResponse(ResponseCode responseCode){
        this.code = responseCode.getCode();
        this.message = responseCode.getComment();
    }

    public BaseResponse(int code, String message){
        this.code =code;
        this.message = message;
    }

    /**
     * 只有当module是集合类型时候才需要将集合大小赋值给totalCount字段
     * @param module
     */
    public BaseResponse(T module){
        this.module = module;
        this.code = ResponseCode.RESPONSE_SUCCESS.getCode();
        this.message = ResponseCode.RESPONSE_SUCCESS.getComment();
    }

    public T getModule() {
        return module;
    }
    public void setModule(T module) {
        this.module = module;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setCode(int code){
        this.code = code;
    }
    public long getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isSuccess() {
        return ResponseCode.RESPONSE_SUCCESS.getCode() == this.getCode();
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "module=" + module +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", totalCount=" + totalCount +
                '}';
    }

    public int getCode() {
        return this.code;
    }
}
