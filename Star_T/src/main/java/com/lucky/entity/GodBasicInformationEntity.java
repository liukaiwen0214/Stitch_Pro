package com.lucky.entity;

import lombok.Data;

@Data
public class GodBasicInformationEntity {
    private Integer god_id;
    private String god_name;
    private int god_rarity;

    @Override
    public String toString() {
        return "GodBasicInformation{" +
                "god_id=" + god_id +
                ", god_name='" + god_name + '\'' +
                ", god_rarity=" + god_rarity +
                '}';
    }
}
