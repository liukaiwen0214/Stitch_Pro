package com.lucky.service.impl;

import com.lucky.service.GodService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@SpringBootTest
class GodServiceImplTest {
    @Autowired
    private GodService godService;

    @Test
    void getRandomGod() {
        System.out.println(godService.getRandomGod().getStory().get(1));;
    }
}