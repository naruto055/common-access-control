package com.naruto.pinda.zuul.api;


import com.naruto.auth.base.R;
import com.naruto.pinda.authority.dto.auth.ResourceQueryDTO;
import com.naruto.pinda.authority.entity.auth.Resource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 远程调用资源服务
 *
 * @Author: naruto
 * @CreateTime: 2025-11-09-1:59
 */
@FeignClient(name = "${pinda.feign.authority-server:auth-server}",
        fallback = ResourceApiFallback.class)
public interface ResourceApi {
    // 获取所有需要鉴权的资源
    @GetMapping("/resource/list")
    public R<List> list();

    // 查询当前登录用户拥有的资源权限
    @GetMapping("/resource")
    public R<List<Resource>> visible(ResourceQueryDTO resourceQueryDTO);
}
