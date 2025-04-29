package com.lucky.entity;

import lombok.Data;

/**
 * 用户实体类
 */
@Data
public class AuthUsersEntity {
    private Integer user_id;
    private String user_name;
    private String user_email;
    private String user_password;
    private String user_iphone;
    private String user_descriptive;
}
