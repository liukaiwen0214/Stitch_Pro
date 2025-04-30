package com.lucky.entity;

import lombok.Data;

import java.util.List;

/**
 * 随机式神实体类
 */
@Data
public class RandomGodEntity {
    //式神ID
    private Integer god_id;
    //式神名称
    private String god_name;
    /**
     * 式神稀有度
     * 1------N
     * 2------R
     * 3------SR
     * 4------SSR
     * 5------SP
     */
    private Integer god_rarity;
    //式神配音cv
    private String cv;
    //式神传记
    private List<String> storys;
    //式神头像
    private String godavatar;
    //式神技能1 图标
    private String skill1icon;
    //式神技能1 名字
    private String skill1name;
    //式神技能1 描述
    private String skill1descriptive;
    //式神技能2 图标
    private String skill2icon;
    //式神技能2 名字
    private String skill2name;
    //式神技能2 描述
    private String skill2descriptive;
    //式神技能3 图标
    private String skill3icon;
    //式神技能3 名字
    private String skill3name;
    //式神技能3 描述
    private String skill3descriptive;
}
