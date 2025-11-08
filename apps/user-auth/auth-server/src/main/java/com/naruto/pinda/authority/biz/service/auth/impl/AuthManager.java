package com.naruto.pinda.authority.biz.service.auth.impl;


import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.naruto.auth.auth.server.utils.JwtTokenServerUtils;
import com.naruto.auth.auth.utils.JwtUserInfo;
import com.naruto.auth.auth.utils.Token;
import com.naruto.auth.base.R;
import com.naruto.auth.common.constant.CacheKey;
import com.naruto.auth.dozer.DozerUtils;
import com.naruto.auth.exception.code.ExceptionCode;
import com.naruto.pinda.authority.biz.service.auth.ResourceService;
import com.naruto.pinda.authority.biz.service.auth.UserService;
import com.naruto.pinda.authority.dto.auth.LoginDTO;
import com.naruto.pinda.authority.dto.auth.ResourceQueryDTO;
import com.naruto.pinda.authority.dto.auth.UserDTO;
import com.naruto.pinda.authority.entity.auth.Resource;
import com.naruto.pinda.authority.entity.auth.User;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证管理器
 *
 * @Author: naruto
 * @CreateTime: 2025-11-08-23:22
 */
@Service
public class AuthManager {

    @Autowired
    private UserService userService;

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private JwtTokenServerUtils jwtTokenServerUtils;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private CacheChannel cacheChannel;

    /**
     * 登录
     *
     * @param account  用户名
     * @param password 密码
     * @return
     */
    public R<LoginDTO> login(String account, String password) {
        R<User> userR = validatePassword(account, password);
        Boolean isError = userR.getIsError();
        if (isError) return R.fail("认证失败");

        User userDB = userR.getData();
        // 生成token
        R<Token> tokenR = generateToken(userDB);

        // 查询用户的资源权限
        ResourceQueryDTO resourceQueryDTO = new ResourceQueryDTO();
        resourceQueryDTO.setUserId(userDB.getId());
        List<Resource> userVisibleResource = resourceService.findVisibleResource(resourceQueryDTO);

        List<String> userPermissionList = Collections.emptyList();
        if (userVisibleResource != null && !userVisibleResource.isEmpty()) {
            // 用户对应权限（资源权限，返回给前端的）
            userPermissionList = userVisibleResource.stream().map(Resource::getCode).collect(Collectors.toList());

            // 用户对应的权限（给后端网关鉴权的，就是请求方法+请求路径）
            List<String> userAccessResource = userVisibleResource.stream().map(item ->
                    item.getMethod() + item.getUrl()
            ).collect(Collectors.toList());
            cacheChannel.set(CacheKey.USER_RESOURCE, userDB.getId().toString(), userAccessResource);
        }


        LoginDTO loginDTO = LoginDTO.builder()
                .user(dozerUtils.map(userDB, UserDTO.class))
                .token(tokenR.getData())
                .permissionsList(userPermissionList)
                .build();


        // 将用户对应的权限（给后端网关使用的）进行缓存

        // 封装返回结果

        return R.success(loginDTO);
    }

    /**
     * 验证密码
     *
     * @param account
     * @param password
     * @return
     */
    private R<User> validatePassword(String account, String password) {
        // 校验账号，密码是否正确
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));

        // 校验密码
        String md5Hex = DigestUtil.md5Hex(password);
        if (user == null || !md5Hex.equals(user.getPassword())) {
            return R.fail(ExceptionCode.JWT_USER_INVALID);
        }

        return R.success(user);
    }

    /**
     * 生成jwt令牌
     *
     * @param user
     * @return
     */
    private R<Token> generateToken(User user) {
        // 为用户生成jwt令牌
        JwtUserInfo jwtUserInfo = new JwtUserInfo();
        jwtUserInfo.setAccount(user.getAccount());
        jwtUserInfo.setUserId(user.getId());
        jwtUserInfo.setOrgId(user.getOrgId());
        jwtUserInfo.setName(user.getName());

        Token token = jwtTokenServerUtils.generateUserToken(jwtUserInfo, null);
        return R.success(token);
    }
}
