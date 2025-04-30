package com.lucky.util;

public class AllTest {
    // 读取单个环境变量
    private static final String ACCESS_KEY_ID = System.getenv("ACCESS_KEY_ID");
    private static final String ACCESS_KEY_SECRET = System.getenv("ACCESS_KEY_SECRET");
    private static final String ENDPOINT = System.getenv("ENDPOINT");

    public static void main(String[] args) {
        System.out.println("ACCESS_KEY_ID: " + ACCESS_KEY_ID);
        System.out.println("ACCESS_KEY_SECRET: " + ACCESS_KEY_SECRET);
        System.out.println("ENDPOINT: " + ENDPOINT);
    }
}
