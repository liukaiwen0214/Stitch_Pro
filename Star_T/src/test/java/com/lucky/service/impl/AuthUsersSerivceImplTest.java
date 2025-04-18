package com.lucky.service.impl;

import com.lucky.entity.AuthUsersEntity;
import com.lucky.service.AuthUsersSerivce;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@SpringBootTest
class AuthUsersSerivceImplTest {
    @Autowired
    private AuthUsersSerivce authUsersSerivce;

    @Test
    void authenticateUser() {
//        authUsersSerivce.authenticateUser();
    }

    @Test
    void registerUser() {
        AuthUsersEntity users = new AuthUsersEntity("liukaiwen","lkwyouxiang@126.com","My148632.","18501973429","");
        if(authUsersSerivce.registerUser(users)){
            System.out.println("添加用户成功");
        }
    }
}