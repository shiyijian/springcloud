package com.web.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class HeadFilter extends ZuulFilter {

    /**
     * pre:请求执行之前filter
     * route: 处理请求，进行路由
     * post: 请求处理完成后执行的filter
     * error:出现错误时执行的filter
     * @return
     */
    @Override
    public String filterType() {
        return "post";
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
        //ctx.addZuulResponseHeader("Content-type", "text/html;charset=UTF-8");
        ctx.addZuulResponseHeader("Content-type", "application/json;charset=UTF-8");
        ctx.addZuulResponseHeader("Access-Control-Allow-Origin", "*");
        ctx.addZuulResponseHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        ctx.addZuulResponseHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, " +
                "If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");

        return null;
    }
}
