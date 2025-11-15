package com.naruto.pinda.zuul.filter;


import cn.hutool.core.util.StrUtil;
import com.naruto.auth.base.R;
import com.naruto.auth.common.adapter.IgnoreTokenConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础过滤器，统一抽取一些公共属性和方法
 *
 * @Author: naruto
 * @CreateTime: 2025-11-09-2:11
 */
@Slf4j
public abstract class BaseFilter extends ZuulFilter {

    @Value("${server.servlet.context-path}")
    protected String zuulPrefix;

    /**
     * 判断当前请求uri是否需要忽略（直接放行）
     *
     * @return
     */
    protected boolean isIgnorePath() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

        // /api/user/xxxx
        String requestURI = request.getRequestURI();
        // /user/xxxx
        requestURI = StrUtil.subSuf(requestURI, zuulPrefix.length());
        // /xxxx
        requestURI = StrUtil.subSuf(requestURI, requestURI.indexOf("/", 1));
        return IgnoreTokenConfig.isIgnoreToken(requestURI);
    }

    /**
     * 网关抛异常，不再进行路由，而是直接返回到前端
     */
    protected void errorResponse(String errMsg, int errCode, int httpStatusCode) {
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 设置响应状态码
        currentContext.setResponseStatusCode(httpStatusCode);
        // 设置响应头
        currentContext.addZuulResponseHeader("Content-Type", "application/json;charset=UTF-8");

        if (currentContext.getResponseBody() == null) {
            // 设置响应体
            currentContext.setResponseBody(R.fail(errCode, errMsg).toString());
            // 不进行路由，直接返回
            currentContext.setSendZuulResponse(false);
        }
    }
}
