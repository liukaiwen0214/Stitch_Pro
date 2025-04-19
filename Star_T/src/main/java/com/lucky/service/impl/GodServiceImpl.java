package com.lucky.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lucky.entity.GodEntity;
import com.lucky.service.GodService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


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
//        getGodImage(getAllList());
//        GodEntity top3 = getTop3(id);
        GodEntity bottom2 = getBottom2(id);
//        godEntity.setId(top3.getId());
//        godEntity.setName(top3.getName());
//        godEntity.setLevel(top3.getLevel());
        godEntity.setCv(bottom2.getCv());
        godEntity.setStory(bottom2.getStory());
        return godEntity;
    }

    @Override
    public List<Map<String, Object>> getGodCount() {
        // 使用 Stream API 将 Map<String, Integer> 转换为 Map<String, Object>
        Map<String, Object> godList = getGodList().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<Map<String, Object>> result = new ArrayList<>();
        for (String rarity : List.of("N", "R", "SR", "SSR", "SP")) {
            Object count = godList.get(rarity);
            if (count != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("count", count);
                map.put("rarity", rarity);
                result.add(map);
            }
        }
        return result;
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

    /**
     * 获取前三个参数值id，name，level
     *
     * @return 返回一个对象
     */
    public Map<String, Integer> getGodList() {
        GodEntity godEntity = new GodEntity();
        Map<String, Integer> map = new HashMap<>();
        // 计数
        map.put("N", 0);
        map.put("R", 0);
        map.put("SR", 0);
        map.put("SSR", 0);
        map.put("SP", 0);

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
                    String json_level = obj.getString("level");
                    switch (json_level) {
                        case "N" -> map.put("N", map.get("N") + 1);
                        case "R" -> map.put("R", map.get("R") + 1);
                        case "SR" -> map.put("SR", map.get("SR") + 1);
                        case "SSR" -> map.put("SSR", map.get("SSR") + 1);
                        case "SP" -> map.put("SP", map.get("SP") + 1);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public static void getGodImage(List<Integer> id) {
        String imageUrl = "https://yys.res.netease.com/pc/zt/20161108171335/data/shishen/";
        String savePath = "/Users/Stitch/Documents/刘凯文的MacBook Air/PersonalSpace/stitch_pro/Star_T/src/main/webapp/static/image/godAvatar/";
        File directory = new File(savePath);
        if (!directory.exists()) {
            directory.mkdirs(); // 创建多级目录
        }
        for (int i = 0; i < id.size(); i++) {
            String fileName = id.get(i) + ".png";
            File file = new File(savePath + fileName);
            if (file.exists()) {
//                System.out.println("图片 " + fileName + " 已存在，跳过下载。");
                continue;
            }
            try (InputStream inputStream = new URL(imageUrl + fileName).openStream();
                 FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
                System.out.println("图片下载成功！保存路径：" + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("图片 " + fileName + " 下载失败：" + e.getMessage());
            }
        }
    }
}
