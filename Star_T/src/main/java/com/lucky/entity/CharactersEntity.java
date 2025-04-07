package com.lucky.entity;

import com.lucky.util.Rarity;

public class CharactersEntity {
    private int shikigami_id;
    private String name;
    private Rarity rarity;
    private String icon_name;

    public CharactersEntity() {
    }

    public CharactersEntity(int shikigami_id, String name, Rarity rarity, String icon_name) {
        this.shikigami_id = shikigami_id;
        this.name = name;
        this.rarity = rarity;
        this.icon_name = icon_name;
    }

    public int getShikigami_id() {
        return shikigami_id;
    }

    public void setShikigami_id(int shikigami_id) {
        this.shikigami_id = shikigami_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public String getIcon_name() {
        return icon_name;
    }

    public void setIcon_name(String icon_name) {
        this.icon_name = icon_name;
    }

    @Override
    public String toString() {
        return "CharactersEntity{" +
                "shikigami_id=" + shikigami_id +
                ", name='" + name + '\'' +
                ", rarity=" + rarity +
                ", icon_name='" + icon_name + '\'' +
                '}';
    }
}
