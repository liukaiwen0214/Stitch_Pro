package com.lucky.controller;

import com.lucky.entity.DetailedGodEntity;
import com.lucky.entity.GodBasicInformationEntity;
import com.lucky.service.PossessGodService;
import com.lucky.util.AliyunOSSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 式神页面
 */
@Controller
@RequestMapping("/ShikigmainDisplay")
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
    @RequestMapping("/temporaryTestPage")
    public String temporaryTestPage() {
        return "/MainMenu-New";
    }

    @GetMapping("/reckoning")
    @ResponseBody
    public List<Map<String, Object>> countCharactersByRarity() {
        logger.info("获取式神稀有度数量统计成功！");
        return possessGodService.godCount();
    }

    @GetMapping("/randshikigma")
    @ResponseBody
    public DetailedGodEntity getRandomGod() {
        DetailedGodEntity god = possessGodService.random_god();
        logger.info("查询随机式神成功，随机式神为:{}", god.getGod_name());
        return god;
    }

    @GetMapping("/allgods")
    @ResponseBody
    public List<Map<String, String>> allGods() {
        List<Map<String, String>> godlist = new ArrayList<>();
        List<GodBasicInformationEntity> godBasics = possessGodService.getGodBasics();
        for (GodBasicInformationEntity gods : godBasics) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(gods.getGod_id()));
            map.put("name", gods.getGod_name());
            switch (gods.getGod_rarity()) {
                case 1:
                    map.put("rarity", "N");
                    break;
                case 2:
                    map.put("rarity", "R");
                    break;
                case 3:
                    map.put("rarity", "SR");
                    break;
                case 4:
                    map.put("rarity", "SSR");
                    break;
                case 5:
                    map.put("rarity", "SP");
                    break;
                default:
                    logger.error("式神稀有度为空！");
                    break;
            }
            map.put("avatar", AliyunOSSUtil.generateFileUrl("stitch-star", "onmyoji/images/godimg/" + gods.getGod_id() + ".png", 60 * 1000));
            godlist.add(map);
        }
        return godlist;
    }
}
