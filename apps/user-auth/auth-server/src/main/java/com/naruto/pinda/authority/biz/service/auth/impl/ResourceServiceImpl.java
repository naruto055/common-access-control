package com.naruto.pinda.authority.biz.service.auth.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naruto.pinda.authority.biz.dao.auth.ResourceMapper;
import com.naruto.pinda.authority.biz.service.auth.ResourceService;
import com.naruto.pinda.authority.dto.auth.ResourceQueryDTO;
import com.naruto.pinda.authority.entity.auth.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资源相关的服务类
 *
 * @Author: naruto
 * @CreateTime: 2025-11-09-0:12
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    /**
     * 查询用的户可见的资源
     *
     * @param resourceQueryDTO 查询资源表所需的参数
     * @return
     */
    @Override
    public List<Resource> findVisibleResource(ResourceQueryDTO resourceQueryDTO) {
        return this.getBaseMapper().findVisibleResource(resourceQueryDTO);
    }
}
