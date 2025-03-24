package com.lucky.mapper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@SpringBootTest
class UserEntityMapperTest {
    @Autowired
    UserEntityMapper userEntityMapper;

    @Test
    void selectUserEntityByEmail() {
        System.out.println("selectUserEntityByEmail");
        System.out.println(userEntityMapper.selectUserEntityByEmail("admin@126.com").toString());
    }
}