package com.naruto.pinda.authority.biz.dao.auth;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naruto.pinda.authority.entity.auth.User;
import org.springframework.stereotype.Repository;

/**
 * 用户实体类的mapper
 *
 * @Author: naruto
 * @CreateTime: 2025-11-08-23:27
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
