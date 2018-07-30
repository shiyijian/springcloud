package com.web.cloud.feign.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.web.cloud.common.constant.ResponseCode;
import com.web.cloud.common.constant.UserAuthLevel;
import com.web.cloud.feign.annotation.ApiVersion;
import com.web.cloud.feign.manager.AuthManager;
import com.web.cloud.feign.model.AuthTokenDTO;
import com.web.cloud.feign.response.MopResponse;
import com.web.cloud.feign.util.MopApiUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

@RestController
public class TokenController {

    @Resource
    private AuthManager authManager;

    @RequestMapping(value = "/auth/session_token/get",method = {RequestMethod.POST,RequestMethod.GET})
    public MopResponse getSessionToken(HttpServletRequest request){
        String appKey = request.getParameter("app_key");

        String sessionToken = authManager.allocateSessionToken();

        Map<String,Object> params = Maps.newHashMap();
        params.put("session_token",sessionToken);

        return MopApiUtil.getResponse(params, UserAuthLevel.LEVEL_GUEST);
    }

    @RequestMapping(value = "/auth/session_token/get",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiVersion(2.0)
    public MopResponse getSessionTokenV2(HttpServletRequest request){
        Map<String,Object> params = Maps.newHashMap();
        params.put("session_token","123");

        return MopApiUtil.getResponse(params, UserAuthLevel.LEVEL_GUEST);
    }

    @RequestMapping(value = "/auth/access_token/refresh",method = RequestMethod.POST)
    public MopResponse refreshAccessToken(HttpServletRequest request){
        String appKey = request.getParameter("app_key");
        String refreshToken = request.getParameter("refresh_token");

        if(Strings.isNullOrEmpty(refreshToken)){
            return MopApiUtil.getResponse(ResponseCode.P_E_REFRESH_TOKEN_MISSING);
        }

        String typeUserId = authManager.getUserIdByRefreshToken(refreshToken);
        if(typeUserId == null){//refresh token 失效
            return new MopResponse(ResponseCode.P_E_REFRESH_TOKEN_INVALID);
        }else {
            Integer type = Integer.valueOf(typeUserId.split(",")[0]);
            Long userId = Long.valueOf(typeUserId.split(",")[1]);
            AuthTokenDTO authTokenDTO = null;
            if(type == 1){
                //管理员
                authTokenDTO = authManager.allocateAdminToken(userId);
            }else if(type == 2){
                //用户
                authTokenDTO = authManager.allocateUserToken(userId);
            }else if(type == 3){
                authTokenDTO = authManager.allocateEnterpriseAdminToken(userId);
            }

            Map<String, Serializable> map = authManager.getAttributeMap(refreshToken);
            authManager.setAttributeMap(authTokenDTO.getAccessToken(),map);
            authManager.setAttributeMap(authTokenDTO.getRefreshToken(),map);
            MopResponse<AuthTokenDTO> resp = new MopResponse<AuthTokenDTO>(authTokenDTO);
            return resp;
        }
    }

    @RequestMapping(value = "/user/logout",method = {RequestMethod.GET,RequestMethod.POST})
    public MopResponse userLogout(HttpServletRequest request){
        String appKey = request.getParameter("app_key");
        Long userId = (Long)request.getAttribute("userId");
        Integer type = (Integer)request.getAttribute("type");

        if(userId == null){
            return MopApiUtil.getResponse(ResponseCode.P_E_PARAM_ISNULL,"用户id不能为空");
        }
        if(type == null){
            return MopApiUtil.getResponse(ResponseCode.P_E_PARAM_ISNULL,"用户类型不能为空");
        }

        authManager.kickToken(userId,type);

        return MopApiUtil.getResponse(null);
    }
}
