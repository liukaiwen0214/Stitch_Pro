package com.lucky.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lucky.entity.GodEntity;
import com.lucky.service.GodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class GodServiceImpl implements GodService {

    String all_shishen = "https://yys.res.netease.com/pc/zt/20161108171335/js/app/all_shishen.json";
    String getIdGod = "https://g37simulator.webapp.163.com/get_hero_story?heroid=";

    @Override
    public GodEntity getRandomGod() {
        GodEntity godEntity = new GodEntity();
        Random random = new Random();
        int randomIndex = random.nextInt(getAllList().size());
        int id = getAllList().get(randomIndex);
        GodEntity top3 = getTop3(id);
        GodEntity bottom2 = getBottom2(id);
        godEntity.setId(top3.getId());
        godEntity.setName(top3.getName());
        godEntity.setLevel(top3.getLevel());
        godEntity.setCv(bottom2.getCv());
        godEntity.setStory(bottom2.getStory());
        return godEntity;
    }

    /**
     * 获取所有式神ID
     *
     * @return 返回ID集合
     */
    public List<Integer> getAllList() {
        List<Integer> idList = new ArrayList<>();
        try {
            URL url = new URL(all_shishen);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 创建 BufferedReader 读取响应内容
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                JSONArray jsonArray = JSONArray.parseArray(response.toString());
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.containsKey("id")) {
                        idList.add(Integer.valueOf(jsonObject.getString("id")));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return idList;
    }

    /**
     * 获取前三个参数值id，name，level
     *
     * @param id 获取id的参数值
     * @return 返回一个对象
     */
    public GodEntity getTop3(int id) {
        GodEntity godEntity = new GodEntity();
        try {
            URL url = new URL(all_shishen);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
                JSONArray jsonArray = JSONArray.parseArray(response.toString());
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String json_id = obj.getString("id");
                    if (id == Integer.parseInt(json_id)) {
                        godEntity.setLevel(obj.getString("level"));
                        godEntity.setName(obj.getString("name"));
                        godEntity.setId(Integer.parseInt(obj.getString("id")));
                        break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return godEntity;
    }

    /**
     * 获取后两个参数cv，story
     *
     * @param id 获取id的参数值
     * @return 返回一个对象
     */
    public GodEntity getBottom2(int id) {
        GodEntity godEntity = new GodEntity();
        try {
            // 创建 URL 对象
            URL url = new URL(getIdGod + id);
            // 打开 HTTP 连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法为 GET
            connection.setRequestMethod("GET");

            // 检查响应状态码
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 创建 BufferedReader 读取响应内容
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                // 逐行读取响应内容
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                // 关闭 BufferedReader
                reader.close();
                // 直接解析响应内容为 JSONObject
                JSONObject jsonObject = JSON.parseObject(response.toString());
                // 获取 data 字段
                JSONObject data = jsonObject.getJSONObject("data");
                // 提取 cv 和 story 信息
                godEntity.setCv(data.getString("cv"));
                godEntity.setStory(data.getList("story", String.class));
            }
        } catch (IOException e) {
            // 打印异常信息
            e.printStackTrace();
            // 可以考虑抛出更具体的异常或者记录日志
        }
        return godEntity;
    }
}
