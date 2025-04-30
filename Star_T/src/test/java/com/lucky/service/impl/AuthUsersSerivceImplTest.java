package com.lucky.service.impl;

import com.lucky.entity.AuthUsersEntity;
import com.lucky.service.AuthUsersSerivce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:SpringMVC.xml"})
 public class AuthUsersSerivceImplTest {
    @Autowired
    private AuthUsersSerivce authUsersSerivce;

    private final Logger logger = LoggerFactory.getLogger(AuthUsersSerivceImplTest.class.getName());
    @Test
    public void authenticateUser() {
        AuthUsersEntity users = new AuthUsersEntity();
        users.setUser_name("admin");
        users.setUser_password("admin");
        if (authUsersSerivce.authenticateUser(users)) {
            logger.info("登录成功");
        } else {
            System.out.println("登录失败，账号密码错误");
        }

    }
}