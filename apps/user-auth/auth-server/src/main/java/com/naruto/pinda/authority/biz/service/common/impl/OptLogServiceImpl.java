package com.naruto.pinda.authority.biz.service.common.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naruto.auth.dozer.DozerUtils;
import com.naruto.auth.log.entity.OptLogDTO;
import com.naruto.pinda.authority.biz.dao.common.OptLogMapper;
import com.naruto.pinda.authority.biz.service.common.OptLogService;
import com.naruto.pinda.authority.entity.common.OptLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 记录用户操作的service
 *
 * @Author: naruto
 * @CreateTime: 2025-11-09-1:23
 */
@Service
public class OptLogServiceImpl extends ServiceImpl<OptLogMapper, OptLog> implements OptLogService {

    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 记录用户操作日志
     * @param optLogDTO
     */
    @Override
    public void save(OptLogDTO optLogDTO) {
        baseMapper.insert(dozerUtils.map(optLogDTO, OptLog.class));
    }
}
