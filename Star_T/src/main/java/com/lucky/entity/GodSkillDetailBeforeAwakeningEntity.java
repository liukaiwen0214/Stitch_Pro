package com.lucky.entity;

import lombok.Data;

/**
 * 式神技能详细信息表（觉醒前）
 */
@Data
public class GodSkillDetailBeforeAwakeningEntity {
    private Integer god_id;
    private Integer skill_id;
    private String skill_level;
    private String skill_detail;
}