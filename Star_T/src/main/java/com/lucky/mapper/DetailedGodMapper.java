package com.lucky.mapper;

import com.lucky.entity.DetailedGodEntity;

import java.util.List;

public interface DetailedGodMapper {
    /**
     * 获取随机式神
     * @param god_id 式神ID
     * @return 随机式神对象
     */
    List<DetailedGodEntity> getRandomGod(Integer god_id);
}
