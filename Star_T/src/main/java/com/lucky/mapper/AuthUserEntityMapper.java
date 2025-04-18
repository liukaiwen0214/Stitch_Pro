package com.lucky.mapper;

import com.lucky.entity.AuthUsersEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthUserEntityMapper {
    /**
     * 前端触发登陆时，调用authenticateUser方法获取用户与密码是否与数据库所匹配
     * 用户可以使用，用户名+密码，邮箱+密码，手机号+密码；
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @param phone 手机号
     * @return 返回>0为登陆成功，反之为登陆失败
     */
    int authenticateUser(String username, String password, String email, String phone);

    /**
     * 添加用户
     * @param authUsersEntity 用户对象
     * @return 返回>0为登陆成功，反之为登陆失败
     */
    int increaseUser(AuthUsersEntity authUsersEntity);

    /**
     * 添加用户时查找用户是否存在
     * @param authUsersEntity 提供一个用户查询
     * @return 返回>0为登陆成功，反之为登陆失败
     */
    Integer consultUser(AuthUsersEntity authUsersEntity);

    /**
     * 前端返回ID查询数据库中最大的ID是多少，添加是增加1
     * @return 最大ID
     */
    Integer consultUser_id();

}
