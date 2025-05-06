package com.lucky.service;

import com.lucky.entity.DetailedGodEntity;

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

}
