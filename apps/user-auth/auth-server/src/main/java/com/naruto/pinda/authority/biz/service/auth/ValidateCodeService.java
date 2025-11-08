package com.naruto.pinda.authority.biz.service.auth;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证服务
 *
 * @Author: naruto
 * @CreateTime: 2025-11-08-22:11
 */
public interface ValidateCodeService {

    /**
     * 创建验证码
     *
     * @param key      用户请求的标识
     * @param response
     */
    void create(String key, HttpServletResponse response) throws IOException;

    /**
     * 校验验证码
     *
     * @param key  用户请求的标识
     * @param code 用户输入的验证码
     * @return
     */
    boolean check(String key, String code);
}
