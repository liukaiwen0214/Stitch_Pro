package com.lucky.service.impl;

import com.lucky.mapper.UserEntityMapper;
import com.lucky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Override
    public int user_login(String username, String password) {
        return userEntityMapper.getUserEntity(username, password);
    }
}
