package com.lucky.service;

import com.lucky.entity.AuthUsersEntity;

public interface AuthUsersSerivce {
    /**
     * 用户登陆
     * @param username 用户名
     * @param password 用户密码
     * @param email 用户邮箱
     * @param phone 用户手机
     * @return 是否存在数据库
     */
    boolean authenticateUser(String username, String password, String email, String phone);

    /**
     * 用户新增
     * @param authUsersEntity 用户对象
     * @return 增加成功/失败
     */
    boolean registerUser(AuthUsersEntity authUsersEntity);
}
