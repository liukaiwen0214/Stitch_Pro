package com.lucky.mapper;

import com.lucky.entity.GodSkillDetailAfterAwakeningEntity;

import java.util.List;

public interface GodSkillDetailAfterAwakeningMapper {
    /**
     * 添加技能时判断id是否存在
     * @param skill_id 技能id
     * @return 大于零则存在，就不添加了
     */
    int consultSkillId(Integer skill_id);

    /**
     * 添加技能
     * @param gsdaae 技能对象
     * @return 变更行数，大于0为成功
     */
    int addSkillBasicBeforeAwakening(GodSkillDetailAfterAwakeningEntity gsdaae);

    /**
     * 添加觉醒后技能时，如果技能没有发生变动，只是添加了属性变化，则使用式神ID添加一次属性变化
     * @param god_id 式神ID
     * @return 变更行数，大于0为成功
     */
    int consultGodId(Integer god_id);

    /**
     * 添加技能的时候，如果之前添加过用户ID，并且技能ID为空，则修改本条记录
     * @param gsdaae 技能对象
     * @return 变更行数，大于0为成功
     */
    int updateSkillBasicBeforAwakening(GodSkillDetailAfterAwakeningEntity gsdaae);

    /**
     * 获取技能图标
     * @return 图标名
     */
    List<String> consultIcon();
}