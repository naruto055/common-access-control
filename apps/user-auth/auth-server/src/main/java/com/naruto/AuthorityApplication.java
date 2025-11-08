package com.naruto;

import com.naruto.auth.auth.server.EnableAuthServer;
import com.naruto.auth.user.annotation.EnableLoginArgResolver;
import com.naruto.auth.validator.config.EnableFormValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 权限服务启动类
 *
 * @author wenqunsheng
 * @date 2025/11/7 17:30
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(value = {
        "com.naruto.pinda",
})
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableFormValidator
@EnableAuthServer  // 引入封装好的认证服务
@EnableLoginArgResolver  // 开启自动登录用户对象注入
public class AuthorityApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = SpringApplication.run(AuthorityApplication.class, args);

        // 启动完成后在控制台提示项目启动成功，并且输出当前服务赌赢的swagger接口文档访问地址
        ConfigurableEnvironment environment = context.getEnvironment();
        String appName = environment.getProperty("spring.application.name");
        String address = InetAddress.getLocalHost().getHostAddress();
        String serverPort = environment.getProperty("server.port");
        log.info("应用{}启动成功，swagger访问地址：http://{}:{}/doc.html", appName, address, serverPort);
    }
}
