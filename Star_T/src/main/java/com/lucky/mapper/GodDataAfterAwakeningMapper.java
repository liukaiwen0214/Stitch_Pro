package com.lucky.mapper;

import com.lucky.entity.GodDataAfterAwakeningEntity;

public interface GodDataAfterAwakeningMapper {
    /**
     * 添加信息时判定是否存在与数据库
     * @param god_id 信息对象
     * @return 1，0
     */
    int consultId(Integer god_id);

    /**
     * 添加信息到数据库
     * @param gdaae 信息对象
     * @return 1，0
     */
    int addGodData(GodDataAfterAwakeningEntity gdaae);
}
