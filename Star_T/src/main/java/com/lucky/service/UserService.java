package com.lucky.service;

import com.lucky.entity.UserEntity;

public interface UserService {
    int user_login(String email, String password);
    int add_user(UserEntity user);
    UserEntity selectUserEntityByEmail(String email);
}
