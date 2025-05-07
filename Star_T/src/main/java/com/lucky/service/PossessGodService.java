package com.lucky.service;

import com.lucky.entity.DetailedGodEntity;
import com.lucky.entity.GodBasicInformationEntity;

import java.util.List;
import java.util.Map;

/**
 * 式神相关所有的接口
 */
public interface PossessGodService {
    /**
     * 获取所有的式神计数，将数据传递给前端，实现图饼展示
     * @return [[R,20],[SR,20]......]
     */
    List<Map<String, Object>> godCount();

    /**
     * 获取主页随机式神
     * @return 一个随机式神对象
     */
    DetailedGodEntity random_god();

    /**
     * 获取所有式神基本信息 式神ID、名称、稀有度
     * @return 式神集合
     */
    List<GodBasicInformationEntity> getGodBasics();
}
