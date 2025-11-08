package com.naruto.pinda.authority.biz.service.auth.impl;


import cn.hutool.core.util.StrUtil;
import com.naruto.auth.common.constant.CacheKey;
import com.naruto.auth.exception.BizException;
import com.naruto.pinda.authority.biz.service.auth.ValidateCodeService;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码服务
 *
 * @Author: naruto
 * @CreateTime: 2025-11-08-22:12
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private CacheChannel cacheChannel;

    /**
     * 创建验证码，同时对验证码进行缓存
     *
     * @param key      用户请求的标识
     * @param response
     */
    @Override
    public void create(String key, HttpServletResponse response) throws IOException {
        Captcha arithmeticCaptcha = new ArithmeticCaptcha();
        String text = arithmeticCaptcha.text();
        // 将验证码写入缓存
        cacheChannel.set(CacheKey.CAPTCHA, key, text);

        // 将验证码图片输出给客户端
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);

        arithmeticCaptcha.out(response.getOutputStream());
    }

    /**
     * 校验验证码
     *
     * @param key  用户请求的标识
     * @param code 用户输入的验证码
     * @return
     */
    @Override
    public boolean check(String key, String code) {
        if (StrUtil.isBlank(key)) throw BizException.validFail("验证码key不能为空");

        CacheObject cacheObject = cacheChannel.get(CacheKey.CAPTCHA, key);
        if (cacheObject.getValue() == null) throw BizException.validFail("验证码已过期");

        String codeInCache = (String) cacheObject.getValue();
        if (!codeInCache.equals(code)) throw BizException.validFail("验证码不正确");
        // 清理验证码
        cacheChannel.evict(CacheKey.CAPTCHA, key);

        return true;
    }
}
