package com.web.cloud.zuul.filter;

import com.google.common.base.Strings;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.web.cloud.common.constant.ResponseCode;
import com.web.cloud.common.util.JsonUtil;
import com.web.cloud.common.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class RequestParamsFilter extends ZuulFilter {

    //外部的接口，不需要验证
    private static List<String> freeApiList;

    //不需要验证的接口
    private static List<String> notSessionTokenApiList;

    //不需要登录的接口
    private static List<String> notAccessTokenApiList;

    static {
        freeApiList = new ArrayList<>();

        notSessionTokenApiList = new ArrayList<>();
        notSessionTokenApiList.add("/cloud/auth/session_token/get");

        notAccessTokenApiList = new ArrayList<>();
        notAccessTokenApiList.add("/cloud/manager/admin/login");
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String requestPath = request.getRequestURI();
        String appKey = request.getParameter("app_key");
        String apiSign = request.getParameter("api_sign");
        String sessionToken = request.getParameter("session_token");
        String accessToken = request.getParameter("access_token");

        if(freeApiList.contains(requestPath)){
            ctx.addZuulRequestHeader("freeApi","1");
            return null;
        }

        //调用的接口需要验证sessiontoken和accesstoken
        if(!notSessionTokenApiList.contains(requestPath)){
            if(Strings.isNullOrEmpty(sessionToken)){
                //sessionToken不存在
                sendResponse(ctx,ResponseCode.P_E_SESSION_TOKEN_MISSING);
                return null;
            }

            if(!notAccessTokenApiList.contains(requestPath)){
                if(Strings.isNullOrEmpty(accessToken)) {
                    //accessTion不存在
                    sendResponse(ctx, ResponseCode.P_E_ACCESS_TOKEN_MISSING);
                    return null;
                }
                ctx.addZuulRequestHeader("loginApi","1");
            }
        }

        if(Strings.isNullOrEmpty(appKey)){
            sendResponse(ctx,ResponseCode.PARAM_E_PARAM_MISSING,"缺少app_key");
            return null;
        }

        if(Strings.isNullOrEmpty(apiSign)){
            sendResponse(ctx,ResponseCode.PARAM_E_PARAM_MISSING,"缺少api_sign");
            return null;
        }

        return null;
    }

    /**
     * 返回错误
     * @param ctx
     * @param responseCode
     */
    private void sendResponse(RequestContext ctx,ResponseCode responseCode){
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(200);
        ctx.setResponseBody(JsonUtil.toJson(ResponseUtil.getErrorResponse(responseCode)));
    }

    private void sendResponse(RequestContext ctx,ResponseCode responseCode,String message){
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(200);
        ctx.setResponseBody(JsonUtil.toJson(ResponseUtil.getErrorResponse(responseCode,message)));
    }
}
