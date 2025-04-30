package com.lucky.service.impl;

import com.lucky.entity.GodBasicInformationEntity;
import com.lucky.entity.GodBiographies;
import com.lucky.entity.RandomGodEntity;
import com.lucky.entity.RandomGodThreeClassEntity;
import com.lucky.mapper.GodBasicInformationMapper;
import com.lucky.mapper.RandomGodThreeClassMapper;
import com.lucky.service.PossessGodService;
import com.lucky.util.AliyunOSSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PossessGodServiceImpl implements PossessGodService {
    private final GodBasicInformationMapper gbi;
    private final RandomGodThreeClassMapper rgtcm;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public PossessGodServiceImpl(GodBasicInformationMapper gbi, RandomGodThreeClassMapper rgtcm) {
        this.gbi = gbi;
        this.rgtcm = rgtcm;
    }

    @Override
    public List<Map<String, Object>> godCount() {
        // 使用 Stream API 将 Map<String, Integer> 转换为 Map<String, Object>
        Map<String, Object> godList = getGodList().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<Map<String, Object>> result = new ArrayList<>();
        for (String rarity : List.of("N", "R", "SR", "SSR", "SP")) {
            Object count = godList.get(rarity);
            if (count != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("count", count);
                map.put("rarity", rarity);
                result.add(map);
            }
        }
        return result;
    }

    @Override
    public RandomGodEntity random_god() {
        RandomGodEntity rge = new RandomGodEntity();
        List<RandomGodThreeClassEntity> gods = rgtcm.getRandomGod(random_god_id());
//        List<RandomGodThreeClassEntity> gods = rgtcm.getRandomGod(352);
        //式神ID
        rge.setGod_id(gods.get(0).getGbi().getGod_id());
        //式神名称
        rge.setGod_name(gods.get(0).getGbi().getGod_name());
        //式神稀有度
        rge.setGod_rarity(gods.get(0).getGbi().getGod_rarity());
        //式神配音cv
        rge.setCv(gods.get(0).getGb().getGod_cv());
        //式神传记
        List<String> storys = IntStream.rangeClosed(1, 9).mapToObj(i -> {
            try {
                Method method = GodBiographies.class.getMethod("getStory" + i);
                return (String) method.invoke(gods.get(0).getGb());
            } catch (Exception e) {
                logger.error("Stream API 反射GodBiographies类失败");
                return null;
            }
        }).collect(Collectors.toCollection(ArrayList::new));
        rge.setStorys(storys);
        //式神头像
        rge.setGodavatar(AliyunOSSUtil.generateFileUrl("stitch-star", "onmyoji/images/godimg/" + gods.get(0).getGbi().getGod_id() + ".png", 60 * 1000));
        //开始获取式神技能的信息
        if (gods.get(0) != null) {
            //式神技能1 名称
            rge.setSkill1name(gods.get(0).getGsbba().getSkill_name());
            //式神技能1 图标
            rge.setSkill1icon(AliyunOSSUtil.generateFileUrl("stitch-star", "onmyoji/images/killimg/" + gods.get(0).getGsbba().getSkill_icon() + ".png", 60 * 1000));
            //式神技能1 描述
            rge.setSkill1descriptive(gods.get(0).getGsbba().getSkill_detail());
        } else {
            logger.error("式神没有技能1");
        }
        if (gods.get(1) != null) {
            //式神技能2 名称
            rge.setSkill2name(gods.get(1).getGsbba().getSkill_name());
            //式神技能2 图标
            rge.setSkill2icon(AliyunOSSUtil.generateFileUrl("stitch-star", "onmyoji/images/killimg/" + gods.get(1).getGsbba().getSkill_icon() + ".png", 60 * 1000));
            //式神技能2 描述
            rge.setSkill2descriptive(gods.get(1).getGsbba().getSkill_detail());
        } else {
            logger.error("式神没有技能2");
        }
        if (gods.get(2) != null) {
            //式神技能3 名称
            rge.setSkill3name(gods.get(2).getGsbba().getSkill_name());
            //式神技能3 图标
            rge.setSkill3icon(AliyunOSSUtil.generateFileUrl("stitch-star", "onmyoji/images/killimg/" + gods.get(2).getGsbba().getSkill_icon() + ".png", 60 * 1000));
            //式神技能3 描述
            rge.setSkill3descriptive(gods.get(2).getGsbba().getSkill_detail());
        } else {
            logger.error("式神没有技能3");
        }
        return rge;
    }

    /**
     * 查询数据库，将不同的稀有度的式神数量统计出来
     *
     * @return [N, 12], [R, 20]......
     */
    public Map<String, Integer> getGodList() {
        List<GodBasicInformationEntity> rarity = gbi.getGodRarity();
        //创建map集合用来存放获取的计数信息
        Map<String, Integer> map = new HashMap<>();
        // 计数
        map.put("N", 0);
        map.put("R", 0);
        map.put("SR", 0);
        map.put("SSR", 0);
        map.put("SP", 0);
        for (int rarityCount = 0; rarityCount < rarity.size(); rarityCount++) {
            //式神稀有度，1:N,2:R,3:SR,4:SSR,5:SP
            switch (rarity.get(rarityCount).getGod_rarity()) {
                case 1 -> map.put("N", map.get("N") + 1);
                case 2 -> map.put("R", map.get("R") + 1);
                case 3 -> map.put("SR", map.get("SR") + 1);
                case 4 -> map.put("SSR", map.get("SSR") + 1);
                case 5 -> map.put("SP", map.get("SP") + 1);
            }
        }
        return map;
    }

    public Integer random_god_id() {
        Random random = new Random();
        int randomIndex = random.nextInt(gbi.allGodId().size());
        return gbi.allGodId().get(randomIndex);
    }
}
