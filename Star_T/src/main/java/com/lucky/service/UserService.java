package com.lucky.service;

import com.lucky.entity.UserEntity;

public interface UserService {
    /**
     * 用户登录验证
     * @param email 用户名
     * @param password 密码
     * @return 验证是否成功
     */
    boolean loginValidate(String email, String password);
//    int add_user(UserEntity user);
//    UserEntity selectUserEntityByEmail(String email);
}
