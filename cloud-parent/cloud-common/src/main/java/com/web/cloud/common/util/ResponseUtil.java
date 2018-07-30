package com.web.cloud.common.util;

import com.web.cloud.common.constant.ResponseCode;
import com.web.cloud.common.response.impl.BaseResponse;

public class ResponseUtil {

    public static <T> BaseResponse<T> getSuccessResponse(T model) {
        BaseResponse res = new BaseResponse(model);
        res.setCode(ResponseCode.RESPONSE_SUCCESS.getCode());
        res.setMessage(ResponseCode.RESPONSE_SUCCESS.getComment());
        return res;
    }

    public static BaseResponse getErrorResponse(ResponseCode responseCode) {
        return new BaseResponse(responseCode);
    }

    public static BaseResponse getErrorResponse(ResponseCode responseCode, String message) {
        return new BaseResponse(responseCode, message);
    }

    public static BaseResponse getErrorResponse(int code, String message) {
        return new BaseResponse(code, message);
    }

    public static <T> BaseResponse getSuccessResponse(T model, long totalCount) {
        BaseResponse res = new BaseResponse(model);
        res.setCode(ResponseCode.RESPONSE_SUCCESS.getCode());
        res.setMessage(ResponseCode.RESPONSE_SUCCESS.getComment());
        res.setTotalCount(totalCount);
        return res;
    }
}
