package com.lucky.mapper;

import com.lucky.entity.RandomGodThreeClassEntity;

import java.util.List;

public interface RandomGodThreeClassMapper {
    /**
     * 获取随机式神
     * @param god_id 式神ID
     * @return 随机式神对象
     */
    List<RandomGodThreeClassEntity> getRandomGod(Integer god_id);
}
