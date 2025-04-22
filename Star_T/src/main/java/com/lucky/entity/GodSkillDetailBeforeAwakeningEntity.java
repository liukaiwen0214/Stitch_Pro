package com.lucky.entity;
/**
 * 式神技能详细信息表（觉醒前）
 */
public class GodSkillDetailBeforeAwakeningEntity {
    private Integer god_id;
    private Integer skill_id;
    private String skill_level;
    private String skill_detail;

    public Integer getGod_id() {
        return god_id;
    }

    public void setGod_id(Integer god_id) {
        this.god_id = god_id;
    }

    public Integer getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(Integer skill_id) {
        this.skill_id = skill_id;
    }

    public String getSkill_level() {
        return skill_level;
    }

    public void setSkill_level(String skill_level) {
        this.skill_level = skill_level;
    }

    public String getSkill_detail() {
        return skill_detail;
    }

    public void setSkill_detail(String skill_detail) {
        this.skill_detail = skill_detail;
    }
}