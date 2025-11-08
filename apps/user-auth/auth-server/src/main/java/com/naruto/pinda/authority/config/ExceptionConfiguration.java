package com.naruto.pinda.authority.config;


import com.naruto.auth.common.handler.DefaultGlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 全局异常处理
 *
 * @Author: naruto
 * @CreateTime: 2025-11-08-16:00
 */
@Configuration
// 异常处理我们只处理加了RestController 和 Controller 注解的类
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class ExceptionConfiguration extends DefaultGlobalExceptionHandler {
}
