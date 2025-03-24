package com.lucky.service.impl;

import com.lucky.entity.UserEntity;
import com.lucky.mapper.UserEntityMapper;
import com.lucky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Override
    public int user_login(String email, String password) {
        return userEntityMapper.getUserEntity(email, password);
    }

    @Override
    public int add_user(UserEntity user) {
        return userEntityMapper.addUserEntity(user);
    }

    @Override
    public UserEntity selectUserEntityByEmail(String email) {
        return userEntityMapper.selectUserEntityByEmail(email);
    }
}
