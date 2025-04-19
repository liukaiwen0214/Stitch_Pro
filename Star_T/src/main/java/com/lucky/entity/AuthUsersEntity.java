package com.lucky.entity;

/**
 * 用户实体类
 */
public class AuthUsersEntity {
    private Integer user_id;
    private String user_name;
    private String user_email;
    private String user_password;
    private String user_iphone;
    private String user_descriptive;

    public AuthUsersEntity() {
    }

    public AuthUsersEntity(String user_name, String user_password) {
        this.user_name = user_name;
        this.user_password = user_password;
    }

    public AuthUsersEntity(String user_name, String user_email, String user_password, String user_iphone, String user_descriptive) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_iphone = user_iphone;
        this.user_descriptive = user_descriptive;
    }

    public AuthUsersEntity(Integer user_id, String user_name, String user_email, String user_password, String user_iphone, String user_descriptive) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_iphone = user_iphone;
        this.user_descriptive = user_descriptive;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_iphone() {
        return user_iphone;
    }

    public void setUser_iphone(String user_iphone) {
        this.user_iphone = user_iphone;
    }

    public String getUser_descriptive() {
        return user_descriptive;
    }

    public void setUser_descriptive(String user_descriptive) {
        this.user_descriptive = user_descriptive;
    }

    @Override
    public String toString() {
        return "AuthUsersEntity{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_iphone='" + user_iphone + '\'' +
                ", user_descriptive='" + user_descriptive + '\'' +
                '}';
    }
}
