package com.naruto.pinda.zuul.api;


import com.naruto.auth.base.R;
import com.naruto.pinda.authority.dto.auth.ResourceQueryDTO;
import com.naruto.pinda.authority.entity.auth.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资源服务的熔断器
 *
 * @Author: naruto
 * @CreateTime: 2025-11-09-2:05
 */
@Component
public class ResourceApiFallback implements ResourceApi {
    @Override
    public R<List> list() {
        return null;
    }

    @Override
    public R<List<Resource>> visible(ResourceQueryDTO resourceQueryDTO) {
        return null;
    }
}
