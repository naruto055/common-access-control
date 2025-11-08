package com.naruto.pinda.authority.controller.auth;


import cn.hutool.core.util.StrUtil;
import com.naruto.auth.base.BaseController;
import com.naruto.auth.base.R;
import com.naruto.auth.exception.BizException;
import com.naruto.pinda.authority.biz.service.auth.ValidateCodeService;
import com.naruto.pinda.authority.biz.service.auth.impl.AuthManager;
import com.naruto.pinda.authority.dto.auth.LoginDTO;
import com.naruto.pinda.authority.dto.auth.LoginParamDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证控制器
 *
 * @Author: naruto
 * @CreateTime: 2025-11-08-22:04
 */
@RestController
@RequestMapping("/anno")
@Api(tags = "登录认证控制器", value = "LoginController")
public class LoginController extends BaseController {

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private AuthManager authManager;

    @GetMapping(value = "/captcha", produces = "image/png")
    @ApiOperation(notes = "验证码", value = "验证码")
    public void captcha(@RequestParam String key, HttpServletResponse response) throws IOException {
        if (StrUtil.isBlank(key)) throw BizException.validFail("验证码key不能为空");

        validateCodeService.create(key, response);
    }

    /**
     * 登录
     *
     * @param loginParamDTO 用户登录参数
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(notes = "登录", value = "登录")
    public R<LoginDTO> login(@RequestBody @Validated LoginParamDTO loginParamDTO) {
        // 校验验证码
        boolean check = validateCodeService.check(loginParamDTO.getKey(), loginParamDTO.getCode());

        if (check) {
            // 执行具体的登录认证逻辑
            R<LoginDTO> r = authManager.login(loginParamDTO.getAccount(), loginParamDTO.getPassword());
            return r;
        }

        // 验证码校验不通过，直接返回null
        return this.success(null);
    }

    /**
     * 校验验证码
     *
     * @param loginParamDTO
     * @return
     */
    @PostMapping("/check")
    @ApiOperation(notes = "校验验证码", value = "校验验证码")
    public R<Boolean> check(@RequestBody LoginParamDTO loginParamDTO) {
        boolean check = validateCodeService.check(loginParamDTO.getKey(), loginParamDTO.getCode());
        return R.success(check);
    }
}
