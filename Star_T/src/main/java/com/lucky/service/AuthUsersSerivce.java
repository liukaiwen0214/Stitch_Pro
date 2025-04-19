package com.lucky.service;

import com.lucky.entity.AuthUsersEntity;

public interface AuthUsersSerivce {
    /**
     * 用户登陆
     * @param authUsersEntity 用户
     * @return 是否存在数据库
     */
    boolean authenticateUser(AuthUsersEntity authUsersEntity);

    /**
     * 用户新增
     * @param authUsersEntity 用户对象
     * @return 增加成功/失败
     */
    boolean registerUser(AuthUsersEntity authUsersEntity);
}
