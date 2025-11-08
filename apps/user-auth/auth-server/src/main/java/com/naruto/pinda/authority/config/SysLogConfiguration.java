package com.naruto.pinda.authority.config;


import com.naruto.auth.log.entity.OptLogDTO;
import com.naruto.auth.log.event.SysLogListener;
import com.naruto.pinda.authority.biz.service.common.OptLogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.function.Consumer;

/**
 * 系统操作日志配置类
 *
 * @Author: naruto
 * @CreateTime: 2025-11-09-1:27
 */
@Configuration
@EnableAsync
public class SysLogConfiguration {
    // 创建日志记录监听器对象
    @Bean
    public SysLogListener sysLogListener(OptLogService optLogService) {
        Consumer<OptLogDTO> consumer = opt -> optLogService.save(opt);
        return new SysLogListener(consumer);
    }
}
