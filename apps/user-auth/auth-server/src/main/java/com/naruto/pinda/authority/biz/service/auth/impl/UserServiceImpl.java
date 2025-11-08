package com.naruto.pinda.authority.biz.service.auth.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naruto.pinda.authority.biz.dao.auth.UserMapper;
import com.naruto.pinda.authority.biz.service.auth.UserService;
import com.naruto.pinda.authority.entity.auth.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户相关的服务
 *
 * @Author: naruto
 * @CreateTime: 2025-11-08-23:29
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
