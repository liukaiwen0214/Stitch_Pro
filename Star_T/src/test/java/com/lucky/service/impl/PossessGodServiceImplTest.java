package com.lucky.service.impl;

import com.lucky.service.PossessGodService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:SpringMVC.xml"})
public class PossessGodServiceImplTest {
    @Autowired
    private PossessGodService possessGodService;


    @Test
    public void godCount() {
        for (Map<String, Object> map : possessGodService.godCount()) {
            System.out.println(map);
        }
    }

    @Test
    public void random_god() {
        System.out.println(possessGodService.random_god());
    }
}