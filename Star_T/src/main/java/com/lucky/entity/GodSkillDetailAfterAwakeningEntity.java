package com.lucky.entity;

public class GodSkillDetailAfterAwakeningEntity {
    private Integer god_id;
    private Integer skill_id;
    private String skill_name;
    private String skill_detail;
    private String skill_icon;
    private int skill_add_type;
    private String skill_add;

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

    public int getSkill_add_type() {
        return skill_add_type;
    }

    public void setSkill_add_type(int skill_add_type) {
        this.skill_add_type = skill_add_type;
    }

    public String getSkill_add() {
        return skill_add;
    }

    public void setSkill_add(String skill_add) {
        this.skill_add = skill_add;
    }

    @Override
    public String toString() {
        return "GodSkillDetailAfterAwakeningentity{" +
                "god_id=" + god_id +
                ", skill_id=" + skill_id +
                ", skill_name='" + skill_name + '\'' +
                ", skill_detail='" + skill_detail + '\'' +
                ", skill_icon='" + skill_icon + '\'' +
                ", skill_add_type=" + skill_add_type +
                ", skill_add='" + skill_add + '\'' +
                '}';
    }
}