package com.naruto.pinda.authority.biz.service.auth;


import com.baomidou.mybatisplus.extension.service.IService;
import com.naruto.pinda.authority.dto.auth.ResourceQueryDTO;
import com.naruto.pinda.authority.entity.auth.Resource;

import java.util.List;

/**
 * 资源相关的服务类
 *
 * @Author: naruto
 * @CreateTime: 2025-11-09-0:11
 */
public interface ResourceService extends IService<Resource> {

    /**
     * 查询用的户可见的资源
     *
     * @param resourceQueryDTO 查询资源表所需的参数
     * @return
     */
    List<Resource> findVisibleResource(ResourceQueryDTO resourceQueryDTO);
}
