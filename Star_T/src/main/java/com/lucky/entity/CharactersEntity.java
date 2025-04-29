package com.lucky.entity;

import com.lucky.util.Rarity;
import lombok.Data;

@Data
public class CharactersEntity {
    private int shikigami_id;
    private String name;
    private Rarity rarity;
    private String icon_name;
}
