package com.lucky.task;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lucky.entity.GodBasicInformationEntity;
import com.lucky.entity.GodBiographies;
import com.lucky.mapper.GodBasicInformationMapper;
import com.lucky.mapper.GodBiographiesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ScheduledTask {
    @Autowired
    private GodBasicInformationMapper godmapper;
    @Autowired
    private GodBiographiesMapper gbm;

    private Logger logger = Logger.getLogger(ScheduledTask.class.getName());

    // 每天凌晨 2 点执行
    @Scheduled(cron = "0 0 2 * * ?")
    public void scheduledTask() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("定时任务执行时间: " + sdf.format(new Date()));
        // 这里可以添加你要执行的具体业务逻辑
        System.out.println(godmapper);
        try {
            URL url = new URL("https://yys.res.netease.com/pc/zt/20161108171335/js/app/all_shishen.json");
            String baseStoryUrl = "https://g37simulator.webapp.163.com/get_hero_story?heroid=";
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
                GodBasicInformationEntity god = new GodBasicInformationEntity();
                List<Integer> ids = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    god.setGod_id(Integer.valueOf(jsonArray.getJSONObject(i).getString("id")));
                    ids.add(Integer.valueOf(jsonArray.getJSONObject(i).getString("id")));
                    if (godmapper.consultGod(Integer.valueOf(jsonArray.getJSONObject(i).getString("id"))) > 0) {
                        // System.out.println("式神id存在" + jsonArray.getJSONObject(i).getString("id"));
                        continue;
                    }
                    god.setGod_name(jsonArray.getJSONObject(i).getString("name"));
                    switch (jsonArray.getJSONObject(i).getString("level")) {
                        case "N":
                            god.setGod_rarity(1);
                            break;
                        case "R":
                            god.setGod_rarity(2);
                            break;
                        case "SR":
                            god.setGod_rarity(3);
                            break;
                        case "SSR":
                            god.setGod_rarity(4);
                            break;
                        case "SP":
                            god.setGod_rarity(5);
                            break;
                    }
                    if (godmapper.increaseGod(god) > 0) {
                        System.out.println(jsonArray.getJSONObject(i).getString("name") + "添加成功！");
                    }
                }
                for (int i = 0; i < ids.size(); i++) {
                    GodBiographies godBiographies = new GodBiographies();
                    // 每次循环重新初始化 storyUrl
                    String storyUrl = baseStoryUrl + ids.get(i);
                    URL urls = new URL(storyUrl);
                    HttpURLConnection story = (HttpURLConnection) urls.openConnection();
                    story.setRequestMethod("GET");
                    int res = story.getResponseCode();
                    if (res == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader1 = new BufferedReader(new InputStreamReader(story.getInputStream()));
                        StringBuilder response1 = new StringBuilder();
                        String line1;
                        while ((line1 = reader1.readLine()) != null) {
                            response1.append(line1);
                        }
                        reader1.close();
                        // 直接解析响应内容为 JSONObject
                        JSONObject jsonObject = JSON.parseObject(response1.toString());
                        JSONObject data = jsonObject.getJSONObject("data");
                        if (data == null) {
                            logger.warning("data object is null for god ID: " + ids.get(i));
                            continue;
                        }
                        // 提取 cv 和 story 信息
                        godBiographies.setGod_cv(data.getString("cv"));
                        godBiographies.setGod_id(ids.get(i));
                        List<String> storyList = data.getList("story", String.class);
                        if (storyList != null) {
                            logger.info("storyList的长度是：" + storyList.size());
                            switch (storyList.size()) {
                                case 0:
                                    System.out.println("式神没有传记");
                                    break;
                                case 1:
                                    godBiographies.setStory1(storyList.get(0));
                                    break;
                                case 2:
                                    godBiographies.setStory1(storyList.get(0));
                                    godBiographies.setStory2(storyList.get(1));
                                    break;
                                case 3:
                                    godBiographies.setStory1(storyList.get(0));
                                    godBiographies.setStory2(storyList.get(1));
                                    godBiographies.setStory3(storyList.get(2));
                                    break;
                                case 4:
                                    godBiographies.setStory1(storyList.get(0));
                                    godBiographies.setStory2(storyList.get(1));
                                    godBiographies.setStory3(storyList.get(2));
                                    godBiographies.setStory4(storyList.get(3));
                                    break;
                                case 5:
                                    godBiographies.setStory1(storyList.get(0));
                                    godBiographies.setStory2(storyList.get(1));
                                    godBiographies.setStory3(storyList.get(2));
                                    godBiographies.setStory4(storyList.get(3));
                                    godBiographies.setStory5(storyList.get(4));
                                    break;
                                case 6:
                                    godBiographies.setStory1(storyList.get(0));
                                    godBiographies.setStory2(storyList.get(1));
                                    godBiographies.setStory3(storyList.get(2));
                                    godBiographies.setStory4(storyList.get(3));
                                    godBiographies.setStory5(storyList.get(4));
                                    godBiographies.setStory6(storyList.get(5));
                                    break;
                                case 7:
                                    godBiographies.setStory1(storyList.get(0));
                                    godBiographies.setStory2(storyList.get(1));
                                    godBiographies.setStory3(storyList.get(2));
                                    godBiographies.setStory4(storyList.get(3));
                                    godBiographies.setStory5(storyList.get(4));
                                    godBiographies.setStory6(storyList.get(5));
                                    godBiographies.setStory7(storyList.get(6));
                                    break;
                                case 8:
                                    godBiographies.setStory1(storyList.get(0));
                                    godBiographies.setStory2(storyList.get(1));
                                    godBiographies.setStory3(storyList.get(2));
                                    godBiographies.setStory4(storyList.get(3));
                                    godBiographies.setStory5(storyList.get(4));
                                    godBiographies.setStory6(storyList.get(5));
                                    godBiographies.setStory7(storyList.get(6));
                                    godBiographies.setStory8(storyList.get(7));
                                    break;
                                case 9:
                                    godBiographies.setStory1(storyList.get(0));
                                    godBiographies.setStory2(storyList.get(1));
                                    godBiographies.setStory3(storyList.get(2));
                                    godBiographies.setStory4(storyList.get(3));
                                    godBiographies.setStory5(storyList.get(4));
                                    godBiographies.setStory6(storyList.get(5));
                                    godBiographies.setStory7(storyList.get(6));
                                    godBiographies.setStory8(storyList.get(7));
                                    godBiographies.setStory9(storyList.get(8));
                                    break;
                            }
                            if(gbm.consultGodStory(godBiographies.getGod_id())>0){
                                logger.info(godBiographies.getGod_id()+"在数据库中存在！");
                            }else {
                                if (gbm.increaseGodStory(godBiographies)>0){
                                    System.out.println(godBiographies.getGod_id()+"添加成功！");
                                }else {
                                    System.out.println(godBiographies.getGod_id()+"添加失败");
                                }
                            }
                        } else {
                            System.out.println("s");
                            logger.warning("storyList is null for god ID: " + ids.get(i));
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}