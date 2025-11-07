package com.naruto.auth.log.event;


import com.naruto.auth.log.entity.OptLogDTO;

import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件
 *
 */
public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(OptLogDTO source) {
        super(source);
    }
}
