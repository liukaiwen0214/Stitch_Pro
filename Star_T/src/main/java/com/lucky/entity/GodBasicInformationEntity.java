package com.lucky.entity;

public class GodBasicInformationEntity {
    private Integer god_id;
    private String god_name;
    private int god_rarity;

    public GodBasicInformationEntity() {
    }

    public Integer getGod_id() {
        return god_id;
    }

    public void setGod_id(Integer god_id) {
        this.god_id = god_id;
    }

    public String getGod_name() {
        return god_name;
    }

    public void setGod_name(String god_name) {
        this.god_name = god_name;
    }

    public int getGod_rarity() {
        return god_rarity;
    }

    public void setGod_rarity(int god_rarity) {
        this.god_rarity = god_rarity;
    }


    @Override
    public String toString() {
        return "GodBasicInformation{" +
                "god_id=" + god_id +
                ", god_name='" + god_name + '\'' +
                ", god_rarity=" + god_rarity +
                '}';
    }
}
