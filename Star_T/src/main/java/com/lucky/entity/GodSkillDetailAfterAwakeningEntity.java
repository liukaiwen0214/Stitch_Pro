package com.lucky.entity;

import lombok.Data;

@Data
public class GodSkillDetailAfterAwakeningEntity {
    private Integer god_id;
    private Integer skill_id;
    private String skill_name;
    private String skill_detail;
    private String skill_icon;
    private int skill_add_type;
    private String skill_add;
}