package com.web.cloud.feign.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AuthInfoDTO implements Serializable {

    private String accessToken;

    private Long userId;

    private Integer type;

    private Map<String, Serializable> attributeMap = new HashMap<String, Serializable>();

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Map<String, Serializable> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<String, Serializable> attributeMap) {
        this.attributeMap = attributeMap;
    }
}
