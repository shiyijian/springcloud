package com.web.cloud.feign.manager;

import com.web.cloud.feign.model.AuthTokenDTO;

import java.io.Serializable;
import java.util.Map;

public interface AuthManager {

    public String allocateSessionToken();

    public AuthTokenDTO allocateUserToken(long userId);

    public AuthTokenDTO allocateAdminToken(long userId);

    public AuthTokenDTO allocateEnterpriseAdminToken(long userId);

    public boolean checkToken(String token);

    public String getUserIdByAccessToken(String accessToken);

    public String getUserIdByRefreshToken(String refreshToken);

    public Map<String, Serializable> getAttributeMap(String accessToken);

    public void setAttributeMap(String accessToken, Map<String, Serializable> attributeMap);

    public boolean kickToken(long userId,int type);
}
