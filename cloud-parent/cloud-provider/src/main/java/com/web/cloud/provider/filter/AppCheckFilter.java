package com.web.cloud.provider.filter;

import com.web.cloud.common.manager.AppManager;
import com.web.cloud.common.model.dto.AppInfoDTO;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AppCheckFilter implements Filter {

    @Resource
    private AppManager appManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String appKey = request.getParameter("appKey");

        AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);

        if(appInfoDTO != null) {
            request.setAttribute("bizCode", appInfoDTO.getBizCode());
            request.setAttribute("appName", appInfoDTO.getAppName());
            request.setAttribute("appType", appInfoDTO.getAppType());
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
