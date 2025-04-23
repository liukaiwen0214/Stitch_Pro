package com.lucky.util;



import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class HttpUtils {
    /**
     *
     * @param url 需要处理的url
     * @return 返回一个JsonObject对象
     * @throws Exception 抛出异常
     */
    public JSONObject sendGetRequest(String url) throws Exception {
        // 使用 URI 构建 URL 以避免弃用的构造函数
        URI uri = new URI(url);
        URL basicSkillUrl = uri.toURL();

        HttpURLConnection connection = (HttpURLConnection) basicSkillUrl.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return JSON.parseObject(response.toString(), JSONObject.class);
        } else {
            throw new Exception("HTTP request failed with response code: " + responseCode);
        }
    }
}
