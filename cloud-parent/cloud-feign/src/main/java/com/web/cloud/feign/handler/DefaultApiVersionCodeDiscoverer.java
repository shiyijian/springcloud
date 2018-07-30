package com.web.cloud.feign.handler;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class DefaultApiVersionCodeDiscoverer implements ApiVersionCodeDiscoverer{

    @Override
    public String getVersionCode(HttpServletRequest request) {
        String version = request.getHeader("version");
        if (!StringUtils.hasText(version)) {
            String versionFromUrl = request.getParameter("@version");
            if (StringUtils.hasText(versionFromUrl)) {
                version = versionFromUrl;
            }
        }
        return version;
    }
}
