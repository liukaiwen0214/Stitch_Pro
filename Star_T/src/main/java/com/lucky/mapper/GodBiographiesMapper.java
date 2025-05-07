package com.lucky.mapper;

import com.lucky.entity.GodBiographiesEntity;

public interface GodBiographiesMapper {
    /**
     * 添加式神
     *
     * @param godBiographiesEntity 式神
     * @return 返回>0为添加成功，反之为失败
     */
    int increaseGodStory(GodBiographiesEntity godBiographiesEntity);

    /**
     * 添加式神时判断id是否存在
     * @param god_id 式神id
     * @return 大于零则存在，就不添加了
     */
    int consultGodStory(Integer god_id);
}
