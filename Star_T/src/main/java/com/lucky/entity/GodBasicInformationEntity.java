package com.lucky.entity;

import lombok.Data;

@Data
public class GodBasicInformationEntity {
    //式神ID
    private Integer god_id;
    //式神名称
    private String god_name;
    //式神稀有度
    private int god_rarity;
}
