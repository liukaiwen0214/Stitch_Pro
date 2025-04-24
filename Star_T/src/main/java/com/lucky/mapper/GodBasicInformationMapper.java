package com.lucky.mapper;
import com.lucky.entity.GodBasicInformationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 式神基本信息
 */
@Mapper
public interface GodBasicInformationMapper {
    /**
     * 添加用户
     *
     * @param entity 式神
     * @return 返回>0为添加成功，反之为失败
     */
    int increaseGod(GodBasicInformationEntity entity);

    /**
     * 添加式神时判断id是否存在
     * @param god_id 式神id
     * @return 大于零则存在，就不添加了
     */
    int consultGod(Integer god_id);

    /**
     * 获取所有式神ID
     * @return 返回ID集合
     */
    List<Integer> allGodId();
}