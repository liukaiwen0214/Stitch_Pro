package com.lucky.mapper;


import com.lucky.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserEntityMapper {
    /**
     * 登录判断
     *
     * @param user 用户名
     * @param pwd  密码
     * @return 是否成功
     */
    int getUserEntity(String user, String pwd);

    /**
     * 添加用户
     *
     * @param userEntity 用户类
     * @return 是否成功
     */
    int addUserEntity(UserEntity userEntity);

    /**
     * 登陆是根据前段返回的用户名查询是否在库内
     *
     * @param email 根据用户名查询
     * @return 是否存在
     */
    UserEntity selectUserEntityByEmail(String email);

}
