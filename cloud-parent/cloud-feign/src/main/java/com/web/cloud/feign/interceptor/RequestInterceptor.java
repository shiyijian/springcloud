package com.web.cloud.feign.interceptor;

import com.google.common.base.Strings;
import com.web.cloud.common.constant.LoginType;
import com.web.cloud.common.constant.ResponseCode;
import com.web.cloud.common.model.dto.AppInfoDTO;
import com.web.cloud.common.util.JsonUtil;
import com.web.cloud.feign.manager.AuthManager;
import com.web.cloud.feign.service.AppService;
import com.web.cloud.feign.util.MopApiUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RequestInterceptor implements HandlerInterceptor {

    @Resource
    private AuthManager authManager;

    @Resource
    private AppService appService;

    /**
     * 用户被冻结了，还可以调用的接口
     */
    private static List<String> fronzenAvailableApiList;

    static {
        fronzenAvailableApiList = new ArrayList<>();

        fronzenAvailableApiList.add("/user/logout");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        cleanDateEmpty(request.getParameterMap());

        String appKey = request.getParameter("app_key");
        String apiSign = request.getParameter("api_sign");
        String sessionToken = request.getParameter("session_token");
        String accessToken = request.getParameter("access_token");
        String freeApi = request.getHeader("freeApi");
        String loginApi = request.getHeader("loginApi");
        String requestPath = request.getRequestURI();

        if(freeApi != null && freeApi.equals("1")){
            //return true;
        }

        //判断token是否有效
        if(!Strings.isNullOrEmpty(sessionToken)){
            boolean checkResult = authManager.checkToken(sessionToken);

            if(checkResult == false){
                sendResponse(response, JsonUtil.toJson(MopApiUtil.getResponse(ResponseCode.P_E_SESSION_TOKEN_INVALID)));
                return false;
            }
        }

        AppInfoDTO appInfoDTO = appService.getAppInfoByAppKey(appKey);

        if(appInfoDTO == null){
            sendResponse(response,JsonUtil.toJson(MopApiUtil.getResponse(ResponseCode.B_E_APP_KEY_INVALID)));
            return false;
        }

        //验证接口的签名是否正确
        boolean validateResult = validateApiSign(request.getParameterMap(), appInfoDTO, apiSign);

        if(validateResult == false){
            sendResponse(response,JsonUtil.toJson(MopApiUtil.getResponse(ResponseCode.P_E_API_SIGN_ERROR)));
            return false;
        }

        if(loginApi != null && loginApi.equals("1")){
            //需要验证accesstion
            boolean checkResult = authManager.checkToken(accessToken);

            if(checkResult == false){
                sendResponse(response,JsonUtil.toJson(MopApiUtil.getResponse(ResponseCode.P_E_ACCESS_TOKEN_INVALID)));
                return false;
            }

            String typeUserId = authManager.getUserIdByAccessToken(accessToken);
            if(typeUserId == null){
                sendResponse(response,JsonUtil.toJson(MopApiUtil.getResponse(ResponseCode.P_E_ACCESS_TOKEN_INVALID)));
                return false;
            }

            //1是管理员2是用户3企业管理员
            int type = Integer.valueOf(typeUserId.split(",")[0]);
            Long userId = Long.valueOf(typeUserId.split(",")[1]);

            if(type == LoginType.USER.getValue()){
                /*UserDTO userDTO = readUserService.getUserById(userId,appKey);

                if(userDTO == null){
                    sendResponse(response, JsonUtil.toJson(MopApiUtil.getResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST)));
                    return false;
                }else if(userDTO.getStatus().intValue() == UserStatus.FROZEN.getValue()){
                    //用户被冻结
                    if(!fronzenAvailableApiList.contains(requestPath)){
                        sendResponse(response,JsonUtil.toJson(MopApiUtil.getResponse(ResponseCode.B_E_ACCOUNT_IS_FROZEN)));
                        return false;
                    }
                }*/

                //用户登录，只能是android端，ios端，和h5端
                if(appInfoDTO.getAppType().intValue() != 1 && appInfoDTO.getAppType().intValue() != 2 && appInfoDTO.getAppType().intValue() != 3){
                    sendResponse(response,JsonUtil.toJson(MopApiUtil.getResponse(ResponseCode.B_E_AUTH_LOGIN_LIMIT)));
                    return false;
                }
            }

            //记录用户id，已经用户的类型（用户，管理员，企业管理员）
            request.setAttribute("userId",userId);
            request.setAttribute("type",type);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 清楚客户端传过来的参数左右两边的空格
     * @param params
     */
    private void cleanDateEmpty(Map<String, String[]> params){
        for(String key : params.keySet()){
            String value = params.get(key)[0];

            params.get(key)[0] = value.trim();
        }
    }

    /**
     * 验证接口的签名
     * @param params
     * @param appInfoDTO
     * @param apiSign
     * @return
     */
    private boolean validateApiSign(Map<String, String[]> params, AppInfoDTO appInfoDTO, String apiSign){
        Map<String, String> signMap = new TreeMap<String, String>();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            if ("api_sign".equals(entry.getKey()) == false) {
                signMap.put(entry.getKey(), (entry.getValue())[0]);
            }
        }

        StringBuilder paramSb = new StringBuilder();
        for (Map.Entry<String, String> entry : signMap.entrySet()) {
            paramSb.append(entry.getKey());
            paramSb.append("=");
            paramSb.append(entry.getValue());
            paramSb.append("&");
        }
        paramSb.deleteCharAt(paramSb.length() - 1);

        String appPwd = appInfoDTO.getAppPwd();
        String signStr = appPwd + paramSb.toString() + appPwd;
        String sign = DigestUtils.md5Hex(signStr);

        return sign.equals(apiSign);
    }

    /**
     * 返回数据
     * @param response
     * @param message
     */
    private void sendResponse(HttpServletResponse response,String message) throws Exception{
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.print(message);
        out.flush();
        out.close();
    }
}
