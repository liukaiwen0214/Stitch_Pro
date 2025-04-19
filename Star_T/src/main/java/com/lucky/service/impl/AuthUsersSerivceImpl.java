package com.lucky.service.impl;

import com.lucky.entity.AuthUsersEntity;
import com.lucky.mapper.AuthUserEntityMapper;
import com.lucky.service.AuthUsersSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthUsersSerivceImpl implements AuthUsersSerivce {
    @Autowired
    private AuthUserEntityMapper authUserEntityMapper;

    /**
     * 登陆方法
     *
     * @param authUsersEntity 用户
     * @return 是否成功
     */
    @Override
    public boolean authenticateUser(AuthUsersEntity authUsersEntity) {
        return authUserEntityMapper.authenticateUser(authUsersEntity) > 0;
    }

    /**
     * 添加用户
     *
     * @param authUsersEntity 用户对象
     * @return 是否成功
     * 1、增加时先判断数据库中，前端提供的用户名、邮箱、手机号是否存在与数据库中
     * 2、如果没有存在于数据库中则查询最大的ID是多少，将ID+1后导入数据库
     */
    @Override
    public boolean registerUser(AuthUsersEntity authUsersEntity) {
        if (authUserEntityMapper.consultUser(authUsersEntity) <= 0) {
            System.out.println("1");
            if (authUserEntityMapper.consultUser_id() == null) {
                authUsersEntity.setUser_id(1);
                System.out.println("要添加的用户是2：" + authUsersEntity.toString());
                return authUserEntityMapper.increaseUser(authUsersEntity) > 0;
            }
            authUsersEntity.setUser_id(authUserEntityMapper.consultUser_id() + 1);
            System.out.println("要添加的用户是3：" + authUsersEntity.toString());
            return authUserEntityMapper.increaseUser(authUsersEntity) > 0;
        }
        System.out.println(authUsersEntity.getUser_name() + "用户存在于数据库中");
        return false;
    }
}
