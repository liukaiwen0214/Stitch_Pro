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
    public boolean loginValidate(String email, String password) {
        UserEntity userEntity = userEntityMapper.selectUserEntityByEmail(email);
        return userEntity.getUser_email().equals(email) && userEntity.getUser_password().equals(password);
    }
}
