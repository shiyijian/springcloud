package com.web.cloud.feign.handler;

import javax.servlet.http.HttpServletRequest;

public interface ApiVersionCodeDiscoverer {

    String getVersionCode(HttpServletRequest request);
}
