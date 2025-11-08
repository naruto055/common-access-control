package com.naruto.pinda.authority.config;


import com.naruto.auth.database.datasource.BaseMybatisConfiguration;
import com.naruto.auth.database.properties.DatabaseProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis 的相关配置
 *
 * @Author: naruto
 * @CreateTime: 2025-11-08-16:52
 */
@Configuration
@Slf4j
public class AuthorityMybatisAutoConfiguration extends BaseMybatisConfiguration {
    public AuthorityMybatisAutoConfiguration(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }
}
