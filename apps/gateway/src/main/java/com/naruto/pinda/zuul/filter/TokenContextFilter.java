package com.naruto.pinda.zuul.filter;


import com.naruto.auth.auth.client.properties.AuthClientProperties;
import com.naruto.auth.auth.client.utils.JwtTokenClientUtils;
import com.naruto.auth.auth.utils.JwtUserInfo;
import com.naruto.auth.context.BaseContextConstants;
import com.naruto.auth.exception.BizException;
import com.naruto.auth.utils.StrHelper;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 该过滤器用于解析请求头中的jwt令牌并且将解析出的用户信息放入zuul的header中
 *
 * @Author: naruto
 * @CreateTime: 2025-11-09-2:28
 */
@Component
public class TokenContextFilter extends BaseFilter {

    @Autowired
    private AuthClientProperties authClientProperties;

    @Autowired
    private JwtTokenClientUtils jwtTokenClientUtils;

    // 前置过滤器
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 设置过滤器的执行顺序，数值越大则优先级越低
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

    // 是否执行当前逻辑
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        if (isIgnorePath())
            // 直接放行
            return null;

        // 从请求头中获取jwt令牌
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String userToken = request.getHeader(authClientProperties.getUser().getHeaderName());

        // 解析jwt令牌
        JwtUserInfo userInfo = null;
        try {
            userInfo = jwtTokenClientUtils.getUserInfo(userToken);
        } catch (BizException e) {
            errorResponse(e.getMessage(), e.getCode(), 200);
            return null;
        } catch (Exception e) {
            errorResponse("解析jwt令牌出错", -1, 200);
            return null;
        }

        // 将解析出的用户信息，塞入zuul的header中
        // 3, 将信息放入header
        if (userInfo != null) {
            addHeader(currentContext, BaseContextConstants.JWT_KEY_ACCOUNT,
                    userInfo.getAccount());
            addHeader(currentContext, BaseContextConstants.JWT_KEY_USER_ID,
                    userInfo.getUserId());
            addHeader(currentContext, BaseContextConstants.JWT_KEY_NAME,
                    userInfo.getName());
            addHeader(currentContext, BaseContextConstants.JWT_KEY_ORG_ID,
                    userInfo.getOrgId());
            addHeader(currentContext, BaseContextConstants.JWT_KEY_STATION_ID,
                    userInfo.getStationId());
        }
        return null;
    }

    private void addHeader(RequestContext ctx, String name, Object value) {
        if (StringUtils.isEmpty(value)) {
            return;
        }
        ctx.addZuulRequestHeader(name, StrHelper.encode(value.toString()));
    }
}
