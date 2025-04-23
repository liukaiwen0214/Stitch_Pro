package com.lucky.entity;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("god_data_after_awakening")
public class GodDataBeforeAwakeningEntity {
    private Long godId;
    private JSONObject awakeitem;
    private BigDecimal critpower;
    private String bodyicon;
    private BigDecimal initturnpos;
    private BigDecimal debuffenhance;
    private BigDecimal defense;
    private BigDecimal speed;
    private BigDecimal hurtreboundrate;
    private BigDecimal hurtadditionrate;
    private String headicon;
    private BigDecimal attack;
    private JSONObject score;
    private BigDecimal maxhp;
    private BigDecimal debuffresist;
    private BigDecimal dodge;
    private Integer debuffreflect;
    private BigDecimal revenge;
    private BigDecimal curedstrenthrate;
    private String icon;
    private BigDecimal leechrate;
    private BigDecimal kogainhprate;
    private BigDecimal critrate;
    private BigDecimal curestrenthrate;
    private Integer kogainmp;
    private BigDecimal hurtreductionrate;
}
