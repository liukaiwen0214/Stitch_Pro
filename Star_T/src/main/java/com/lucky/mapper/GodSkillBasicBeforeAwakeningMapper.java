package com.lucky.mapper;

import com.lucky.entity.GodSkillBasicBeforeAwakeningEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 式神基本技能（觉醒前）
 */
@Mapper
public interface GodSkillBasicBeforeAwakeningMapper {
    /**
     * 添加技能时判断id是否存在
     * @param skill_id 技能id
     * @return 大于零则存在，就不添加了
     */
    int consultSkillId(Integer skill_id);

    /**
     * 添加技能
     * @param gsbbae 技能对象
     * @return 变更行数，大于0为成功
     */
    int addSkillBasicBeforeAwakening(GodSkillBasicBeforeAwakeningEntity gsbbae);
}