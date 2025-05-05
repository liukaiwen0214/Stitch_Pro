package com.lucky.controller;

import com.lucky.entity.RandomGodEntity;
import com.lucky.service.PossessGodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 式神页面
 */
@Controller("/ShikigmainDisplay")
public class ShikigmainDisplayController {
    private final PossessGodService possessGodService;
    private final Logger logger = LoggerFactory.getLogger(ShikigmainDisplayController.class.getName());

    public ShikigmainDisplayController(PossessGodService possessGodService) {
        this.possessGodService = possessGodService;
    }


    @RequestMapping("/shikigamis")
    public String godinformation() {
        return "/ShikigamiDisplayPage";
    }

    @GetMapping("/reckoning")
    @ResponseBody
    public List<Map<String, Object>> countCharactersByRarity() {
        logger.info("获取式神稀有度数量统计成功！");
        return possessGodService.godCount();
    }
    @GetMapping("/randshikigma")
    @ResponseBody
    public RandomGodEntity getRandomGod() {
        return possessGodService.random_god();
    }
}
