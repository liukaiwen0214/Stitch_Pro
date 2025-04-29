package com.lucky.entity;

import lombok.Data;

/**
 * + * 式神技能基本信息表（觉醒前）
 * +
 */

@Data
public class GodSkillBasicBeforeAwakeningEntity {
    private Integer god_id;
    private Integer skill_id;
    private String skill_name;
    private String skill_detail;
    private String skill_icon;
}