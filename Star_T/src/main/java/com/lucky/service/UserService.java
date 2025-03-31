package com.lucky.service;


public interface UserService {
    /**
     * 用户登录验证
     * @param email 用户名
     * @param password 密码
     * @return 验证是否成功
     */
    boolean loginValidate(String email, String password);
}
