package com.lucky.mapper;

import com.lucky.entity.OutfitInformationEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutfitInformationMapper {
    /**
     * 根据名字查询御魂
     * @param name 御魂名字
     * @return 御魂对象
     */
    OutfitInformationEntity findByName(String name);

    /**
     * 查询所有御魂
     * @return 御魂对象
     */
    OutfitInformationEntity findAll();

    /**
     * 添加御魂信息
     * @param outfitInformationEntity 御魂对象
     * @return 1成功，0失败
     */
    int addOutfitInformation(OutfitInformationEntity outfitInformationEntity);
}
