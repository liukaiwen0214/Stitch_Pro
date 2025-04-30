package com.lucky.entity;

import lombok.Data;

/**
 *御魂实体类
 */
@Data
public class OutfitInformationEntity {
    //bigint	    御魂ID
    private Integer outfit_id;
    //varchar(255)	御魂名称
    private String outfit_name;
    //text          御魂图标地址
    private String outfit_icon;
    //varchar(255)	御魂类型
    private String outfit_typology;
    //text	        御魂两件套效果
    private String outfit_double_descriptive;
    //text	        御魂四件套效果
    private String outfit_four_descriptive;
    //text          首领御魂单个效果
    private String outfit_boss;
}
