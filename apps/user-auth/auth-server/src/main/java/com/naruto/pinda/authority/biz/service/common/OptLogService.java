package com.naruto.pinda.authority.biz.service.common;


import com.baomidou.mybatisplus.extension.service.IService;
import com.naruto.auth.log.entity.OptLogDTO;
import com.naruto.pinda.authority.entity.common.OptLog;

/**
 * 记录用户操作日志的服务
 *
 * @Author: naruto
 * @CreateTime: 2025-11-09-1:22
 */
public interface OptLogService extends IService<OptLog> {

    void save(OptLogDTO optLogDTO);
}
