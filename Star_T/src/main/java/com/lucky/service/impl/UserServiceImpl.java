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
    public boolean loginValidate(String email, String password) {
        return "admin@126.com".equals(email) && "123456".equals(password);
    }
}
