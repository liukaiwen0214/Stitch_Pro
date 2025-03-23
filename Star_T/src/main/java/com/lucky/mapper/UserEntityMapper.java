package com.lucky.mapper;


public interface UserEntityMapper {
    /**
     * 登录判断
     * @param user 用户名
     * @param pwd 密码
     * @return 是否成功
     */
    int getUserEntity(String user,String pwd);
}
