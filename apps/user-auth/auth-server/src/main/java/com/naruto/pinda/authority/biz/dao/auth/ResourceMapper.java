package com.naruto.pinda.authority.biz.dao.auth;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naruto.pinda.authority.dto.auth.ResourceQueryDTO;
import com.naruto.pinda.authority.entity.auth.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源实体类的mapper
 *
 * @Author: naruto
 * @CreateTime: 2025-11-09-0:10
 */
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 查询用的户可见的资源
     *
     * @param resourceQueryDTO 查询资源表所需的参数
     * @return
     */
    List<Resource> findVisibleResource(ResourceQueryDTO resourceQueryDTO);
}
