package com.lucky.entity;

import lombok.Data;

import java.util.List;

/**
 * 用来生成主页的随机式神展示的实体类
 */
@Data
public class GodEntity {
    /**
     * parameters：id,level,name
     * 通过https://yys.res.netease.com/pc/zt/20161108171335/js/app/all_shishen.json获取
     * parameters：cv,stroy
     * 通过https://g37simulator.webapp.163.com/get_hero_story?callback=jQuery111309156880750013257_1744702506134&heroid=304&_=1744702506135
     */
    //式神ID
    private int id;
    //式神稀有度
    private String level;
    //式神名字
    private String name;
    //式神配音员
    private String cv;
    //式神传记
    private List<String> story;

    @Override
    public String toString() {
        return "GodEntity{" +
                "id=" + id +
                ", level='" + level + '\'' +
                ", name='" + name + '\'' +
                ", cv='" + cv + '\'' +
                ", story=" + story +
                '}';
    }
}
