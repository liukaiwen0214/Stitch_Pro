package com.lucky.entity;

/**
 * + * 式神技能基本信息表（觉醒前）
 * +
 */


public class GodSkillBasicBeforeAwakeningEntity {

    private Integer god_id;
    private Integer skill_id;
    private String skill_name;
    private String skill_detail;
    private String skill_icon;

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

    public String getSkill_name() {
        return skill_name;
    }

    public void setSkill_name(String skill_name) {
        this.skill_name = skill_name;
    }

    public String getSkill_detail() {
        return skill_detail;

    }

    public void setSkill_detail(String skill_detail) {
        this.skill_detail = skill_detail;

    }

    public String getSkill_icon() {
        return skill_icon;

    }

    public void setSkill_icon(String skill_icon) {
        this.skill_icon = skill_icon;

    }

    @Override
    public String toString() {
        return "GodSkillBasicBeforeAwakening{" + "god_id=" + god_id + ", skill_id=" + skill_id + ", skill_name='" + skill_name + '\'' + ", skill_detail='" + skill_detail + '\'' + ", skill_icon='" + skill_icon + '\'' + +'}';
    }
}