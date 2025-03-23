package com.lucky.entity;

public class UserEntity {
    private int user_id;
    private String user_email;
    private String user_password;
    private String story_1;

    public UserEntity() {
    }

    public UserEntity(int user_id, String user_email, String user_password, String story_1) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_password = user_password;
        this.story_1 = story_1;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getStory_1() {
        return story_1;
    }

    public void setStory_1(String story_1) {
        this.story_1 = story_1;
    }

    @Override
    public String toString() {
        return "userEntity{" +
                "user_id=" + user_id +
                ", user_email='" + user_email + '\'' +
                ", user_password='" + user_password + '\'' +
                ", story_1='" + story_1 + '\'' +
                '}';
    }
}
