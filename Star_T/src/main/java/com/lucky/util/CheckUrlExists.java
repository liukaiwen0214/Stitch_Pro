package com.lucky.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckUrlExists {
    public boolean isUrlExists(String urlString) {
        try {
            // 创建 URL 对象
            URL url = new URL(urlString);
            // 打开 HTTP 连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法为 HEAD，只获取响应头信息，不获取响应体，减少网络开销
            connection.setRequestMethod("HEAD");
            // 获取响应状态码
            int responseCode = connection.getResponseCode();
            // 如果状态码为 200，表示链接存在
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            // 发生异常，通常表示链接不存在或网络问题
            return false;
        }
    }
}