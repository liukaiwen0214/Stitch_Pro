package com.lucky.entity;

import lombok.Data;

/**
 * 随机式神实体类
 * gbi.*,
 * gb.*,
 * gsbba.*
 */
@Data
public class RandomGodThreeClassEntity {
    private GodBasicInformationEntity gbi;
    private GodBiographiesEntity gb;
    private GodSkillBasicBeforeAwakeningEntity gsbba;
    private GodSkillDetailAfterAwakeningEntity gsdaa;
}
