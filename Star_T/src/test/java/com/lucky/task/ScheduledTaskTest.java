package com.lucky.task;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@SpringBootTest
class ScheduledTaskTest {

    @Autowired
    ScheduledTask scheduledTask;

    @Test
    void scheduledTask() {
        scheduledTask.scheduledTask();
    }

    @Test
    void acquisition_skills() {
        scheduledTask.acquisition_skills();
    }

    @Test
    void acquisditon_godimages() {
        scheduledTask.acquisditon_godimages();
    }

    @Test
    void acquisditon_godskillimages() {
        scheduledTask.acquisditon_godskillimages();
    }

    @Test
    void acquisition_awakening_skills() {
        scheduledTask.acquisition_awakening_skills();
    }
}