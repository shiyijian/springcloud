package com.web.cloud.feign.manager.impl;

import com.web.cloud.common.constant.LoginType;
import com.web.cloud.feign.manager.AuthManager;
import com.web.cloud.feign.model.AuthInfoDTO;
import com.web.cloud.feign.model.AuthTokenDTO;
import com.web.cloud.feign.util.Utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthManagerImpl implements AuthManager {

    private Map<String, AuthInfoDTO> authInfoMap = new ConcurrentHashMap<String, AuthInfoDTO>();
    private Map<String, AuthTokenDTO> authTokenMap = new ConcurrentHashMap<String, AuthTokenDTO>();

    @Override
    public String allocateSessionToken() {
        long currentTime = System.currentTimeMillis();
        String sessionToken = DigestUtils.md5Hex("" + currentTime);

        authInfoMap.put(sessionToken, new AuthInfoDTO());
        return sessionToken;
    }

    @Override
    public AuthTokenDTO allocateUserToken(long userId) {
        long currentTime = System.currentTimeMillis();
        String accessToken = DigestUtils.md5Hex("" + userId + currentTime);
        String refreshToken = DigestUtils.md5Hex("" + userId + (currentTime + currentTime % 100000));
        AuthTokenDTO authTokenDTO = new AuthTokenDTO(accessToken, refreshToken);

        AuthInfoDTO authInfo = new AuthInfoDTO();
        authInfo.setUserId(userId);
        authInfo.setType(LoginType.USER.getValue());

        authInfoMap.put(accessToken, authInfo);
        authInfoMap.put(refreshToken, authInfo);
        authTokenMap.put(Utils.genAuthTokenCacheKeyForUser(userId), authTokenDTO);
        return authTokenDTO;
    }

    @Override
    public AuthTokenDTO allocateAdminToken(long userId) {
        long currentTime = System.currentTimeMillis();
        String accessToken = DigestUtils.md5Hex("" + userId + currentTime);
        String refreshToken = DigestUtils.md5Hex("" + userId + (currentTime + currentTime % 100000));
        AuthTokenDTO authTokenDTO = new AuthTokenDTO(accessToken, refreshToken);

        AuthInfoDTO authInfo = new AuthInfoDTO();
        authInfo.setUserId(userId);
        authInfo.setType(LoginType.ADMIN.getValue());
        authInfoMap.put(accessToken, authInfo);
        authInfoMap.put(refreshToken, authInfo);
        authTokenMap.put(Utils.genAuthTokenCacheKeyForAdmin(userId), authTokenDTO);
        return authTokenDTO;
    }

    @Override
    public AuthTokenDTO allocateEnterpriseAdminToken(long userId) {
        long currentTime = System.currentTimeMillis();
        String accessToken = DigestUtils.md5Hex("" + userId + currentTime);
        String refreshToken = DigestUtils.md5Hex("" + userId + (currentTime + currentTime % 100000));
        AuthTokenDTO authTokenDTO = new AuthTokenDTO(accessToken, refreshToken);

        //TODO store the authToken
        AuthInfoDTO authInfo = new AuthInfoDTO();
        authInfo.setUserId(userId);
        authInfo.setType(3);
        authInfoMap.put(accessToken, authInfo);
        authInfoMap.put(refreshToken, authInfo);
        authTokenMap.put(Utils.genAuthTokenCacheKeyForEnterpriseAdmin(userId), authTokenDTO);
        return authTokenDTO;
    }

    @Override
    public boolean checkToken(String token) {
        return authInfoMap.containsKey(token);
    }

    @Override
    public String getUserIdByAccessToken(String accessToken) {
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        AuthInfoDTO authInfo = authInfoMap.get(accessToken);
        if (authInfo != null) {
            return authInfo.getType()+","+authInfo.getUserId();
        }
        return null;
    }

    @Override
    public String getUserIdByRefreshToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            return null;
        }
        AuthInfoDTO authInfo = authInfoMap.get(refreshToken);
        if (authInfo != null) {
            return authInfo.getType()+","+authInfo.getUserId();
        }
        return null;
    }

    @Override
    public Map<String, Serializable> getAttributeMap(String accessToken) {
        if (StringUtils.isBlank(accessToken)) {
            return Collections.emptyMap();
        }
        AuthInfoDTO authInfo = authInfoMap.get(accessToken);
        if (authInfo != null) {
            return authInfo.getAttributeMap();
        } else {
            return Collections.emptyMap();
        }
    }

    @Override
    public void setAttributeMap(String accessToken, Map<String, Serializable> attributeMap) {
        if (StringUtils.isBlank(accessToken)) {
            return;
        }
        AuthInfoDTO authInfo = authInfoMap.get(accessToken);
        if (authInfo != null) {
            authInfo.setAttributeMap(attributeMap);
        }
    }

    @Override
    public boolean kickToken(long userId, int type) {
        AuthTokenDTO authTokenDTO = null;
        String genAuthTokenCacheKey = null;
        if(type == LoginType.USER.getValue()){
            //用户
            genAuthTokenCacheKey = Utils.genAuthTokenCacheKeyForUser(userId);
            authTokenDTO = authTokenMap.get(genAuthTokenCacheKey);
        }else if(type == LoginType.ADMIN.getValue()){
            //管理员
            genAuthTokenCacheKey = Utils.genAuthTokenCacheKeyForAdmin(userId);
            authTokenDTO = authTokenMap.get(genAuthTokenCacheKey);
        }else if(type == LoginType.ENTERPRISE.getValue()){
            //企业管理员
            genAuthTokenCacheKey = Utils.genAuthTokenCacheKeyForEnterpriseAdmin(userId);
            authTokenDTO = authTokenMap.get(genAuthTokenCacheKey);
        }

        if (authTokenDTO != null) {
            authInfoMap.remove(authTokenDTO.getAccessToken());
            authInfoMap.remove(authTokenDTO.getRefreshToken());

            if(genAuthTokenCacheKey != null){
                authInfoMap.remove(genAuthTokenCacheKey);
            }
            return true;
        }

        return false;
    }
}
