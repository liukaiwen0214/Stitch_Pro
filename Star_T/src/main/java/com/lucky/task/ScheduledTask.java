package com.lucky.task;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lucky.entity.*;
import com.lucky.mapper.*;
import com.lucky.util.AliyunOSSUtil;
import com.lucky.util.HttpUtils;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ScheduledTask {
    private final GodBasicInformationMapper gbim;
    private final GodBiographiesMapper gbm;
    private final GodSkillBasicBeforeAwakeningMapper gsbbam;
    private final GodSkillDetailBeforeAwakeningMapper gsdbam;
    private final GodSkillDetailAfterAwakeningMapper gsdaam;
    private final GodDataAfterAwakeningMapper gdaam;
    private final GodDataBeforeAwakeningMapper gdbam;

    private final HttpUtils httpUtils = new HttpUtils();

    private final Logger logger = Logger.getLogger(ScheduledTask.class.getName());

    public ScheduledTask(GodBasicInformationMapper gbim, GodBiographiesMapper gbm, GodSkillBasicBeforeAwakeningMapper gsbbam, GodSkillDetailBeforeAwakeningMapper gsdbam, GodSkillDetailAfterAwakeningMapper gsdaam, GodDataAfterAwakeningMapper gdaam, GodDataBeforeAwakeningMapper gdbam) {
        this.gbim = gbim;
        this.gbm = gbm;
        this.gsbbam = gsbbam;
        this.gsdbam = gsdbam;
        this.gsdaam = gsdaam;
        this.gdaam = gdaam;
        this.gdbam = gdbam;
    }

    // 每天凌晨 2 点执行
    @Scheduled(cron = "0 0 2 * * ?")
    public void scheduledTask() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("定时任务执行时间: " + sdf.format(new Date()));
        // 这里可以添加你要执行的具体业务逻辑
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
                    if (gbim.consultGod(Integer.valueOf(jsonArray.getJSONObject(i).getString("id"))) > 0) {
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
                    if (gbim.increaseGod(god) > 0) {
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
//                            logger.info("storyList的长度是：" + storyList.size());
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
                            if (gbm.consultGodStory(godBiographies.getGod_id()) > 0) {
                                logger.info(godBiographies.getGod_id() + "在数据库中存在！");
                            } else {
                                if (gbm.increaseGodStory(godBiographies) > 0) {
                                    System.out.println(godBiographies.getGod_id() + "添加成功！");
                                } else {
                                    System.out.println(godBiographies.getGod_id() + "添加失败");
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
        acquisition_skills();
        acquisditon_godimages();
        acquisditon_godskillimages();
        acquisition_awakening_skills();
        acquisditon_god_data();
    }

    /**
     * 获取技能表
     */
    public void acquisition_skills() {
        //获取式神基本信息,添加ID
        try {
            List<Integer> allGodId = gbim.allGodId();
            for (int idcount = 0; idcount < allGodId.size(); idcount++) {
                String basicUrl = "https://g37simulator.webapp.163.com/get_hero_skill?awake=0&level=0&star=2&heroid=";
                basicUrl = basicUrl + allGodId.get(idcount);
                URL basicSkillUrl = new URL(basicUrl);
                HttpURLConnection connection = (HttpURLConnection) basicSkillUrl.openConnection();
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
                    JSONObject jsonObject = JSON.parseObject(response.toString());
                    JSONObject data = jsonObject.getJSONObject("data");
                    for (int skillcount = 0; skillcount < 3; skillcount++) {
                        if (data.getJSONObject(allGodId.get(idcount) + "" + (skillcount + 1)) != null) {
                            if (gsbbam.consultSkillId(Integer.valueOf(allGodId.get(idcount) + "" + (skillcount + 1))) <= 0) {
                                JSONObject skillJson = data.getJSONObject(allGodId.get(idcount) + "" + (skillcount + 1));
                                GodSkillBasicBeforeAwakeningEntity gsbbae = new GodSkillBasicBeforeAwakeningEntity();
                                gsbbae.setGod_id(allGodId.get(idcount));
                                gsbbae.setSkill_id(Integer.valueOf(allGodId.get(idcount) + "" + (skillcount + 1)));
                                gsbbae.setSkill_name(skillJson.getString("name"));
                                gsbbae.setSkill_icon(skillJson.getString("icon"));
                                gsbbae.setSkill_detail(skillJson.getString("normaldesc"));
                                gsbbam.addSkillBasicBeforeAwakening(gsbbae);
                                List<String> desc = skillJson.getList("desc", String.class);
                                for (String s : desc) {
                                    GodSkillDetailBeforeAwakeningEntity gsdbae = new GodSkillDetailBeforeAwakeningEntity();
                                    gsdbae.setGod_id(allGodId.get(idcount));
                                    gsdbae.setSkill_id(Integer.valueOf(allGodId.get(idcount) + "" + (skillcount + 1)));
                                    String regex = "^Lv.(\\d+)(.*)"; // 正则表达式

                                    Pattern pattern = Pattern.compile(regex);
                                    Matcher matcher = pattern.matcher(s);

                                    if (matcher.matches()) {
                                        String lvPart = matcher.group(1); // 提取 "Lv.数字"
                                        String content = matcher.group(2).trim(); // 提取后面的内容并去除首尾空格
                                        gsdbae.setSkill_level(lvPart);
                                        gsdbae.setSkill_detail(content);
                                    } else {
                                        System.out.println("格式不匹配，无法分割");
                                    }
                                    gsdbam.addGodSkillDetailBeforeAwakening(gsdbae);
                                }
                            } else {
                                System.out.println("技能ID：" + allGodId.get(idcount) + (skillcount + 1) + "在数据库中存在");
                            }
                        } else {
                            System.out.println("技能ID：" + allGodId.get(idcount) + (skillcount + 1) + "为空");
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取式神头像上传到阿里云oss
     */
    public void acquisditon_godimages() {
        String bucketName = "stitch-star";
        for (Integer integer : gbim.allGodId()) {
            // 指定存储位置，例如存储在 images 目录下，文件名为 test.jpg
            String objectName = "onmyoji/images/godimg/" + integer + ".png";
            String fileUrl = "https://yys.res.netease.com/pc/zt/20161108171335/data/shishen/" + integer + ".png";
            AliyunOSSUtil.uploadFileByUrl(bucketName, objectName, fileUrl);
        }
    }

    /**
     * 获取式神技能图标上传阿里云oss
     */
    public void acquisditon_godskillimages() {
        String bucketName = "stitch-star";
        List<String> iconList = gsdaam.consultIcon();
        for (String iconName : iconList) {
            String objectName = "onmyoji/images/killimg/" + iconName + ".png";
            String fileUrl = "https://yys.res.netease.com/pc/zt/20161108171335/data/skill/" + iconName + ".png";
            AliyunOSSUtil.uploadFileByUrl(bucketName, objectName, fileUrl);
        }
    }

    /**
     * 获取觉醒后的技能图标上传阿里云oss
     */
    public void acquisition_awakening_skills() {
        try {
            List<Integer> allGodId = gbim.allGodId();
            for (int idcount = 0; idcount < allGodId.size(); idcount++) {
                String basicUrl = "https://g37simulator.webapp.163.com/get_hero_skill?awake=1&level=0&star=2&heroid=";
                basicUrl = basicUrl + allGodId.get(idcount);
//                System.out.println(basicUrl);
                URL basicSkillUrl = new URL(basicUrl);
                HttpURLConnection connection = (HttpURLConnection) basicSkillUrl.openConnection();
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
                    JSONObject jsonObject = JSON.parseObject(response.toString());
                    JSONObject data = jsonObject.getJSONObject("data");
                    for (int skillcount = 0; skillcount < 3; skillcount++) {
                        if (data.getJSONObject(allGodId.get(idcount) + "" + (skillcount + 1)) != null) {
//                            System.out.println(gsdaam.consultGodId(allGodId.get(idcount))+"------"+gsdaam.consultSkillId(allGodId.get(idcount)));
                            if (gsdaam.consultGodId(allGodId.get(idcount)) > 0 && gsdaam.consultSkillId(allGodId.get(idcount)) <= 0) {
                                JSONObject skillJson = data.getJSONObject(allGodId.get(idcount) + "" + (skillcount + 1));
                                GodSkillDetailAfterAwakeningEntity gsdaae = new GodSkillDetailAfterAwakeningEntity();
//                                System.out.println(allGodId.get(idcount));
                                gsdaae.setGod_id(allGodId.get(idcount));
                                gsdaae.setSkill_id(Integer.valueOf(allGodId.get(idcount) + "" + (skillcount + 1)));
                                gsdaae.setSkill_name(skillJson.getString("name"));
                                gsdaae.setSkill_icon(skillJson.getString("icon"));
                                gsdaae.setSkill_detail(skillJson.getString("normaldesc"));
                                if (data.getString("add") != null) {
                                    gsdaae.setSkill_add(data.getString("add"));
                                }

                                if (data.getString("add_type") != null) {
                                    gsdaae.setSkill_add_type(Integer.parseInt(data.getString("add_type")));
                                }
                                gsdaam.updateSkillBasicBeforAwakening(gsdaae);
//                                System.out.println("调用update");
                                List<String> desc = skillJson.getList("desc", String.class);
                                for (String s : desc) {
                                    GodSkillDetailBeforeAwakeningEntity gsdbae = new GodSkillDetailBeforeAwakeningEntity();
                                    gsdbae.setGod_id(allGodId.get(idcount));
                                    gsdbae.setSkill_id(Integer.valueOf(allGodId.get(idcount) + "" + (skillcount + 1)));
                                    String regex = "^Lv.(\\d+)(.*)"; // 正则表达式

                                    Pattern pattern = Pattern.compile(regex);
                                    Matcher matcher = pattern.matcher(s);
                                    if (matcher.matches()) {
                                        String lvPart = matcher.group(1); // 提取 "Lv.数字"
                                        String content = matcher.group(2).trim(); // 提取后面的内容并去除首尾空格
                                        gsdbae.setSkill_level(lvPart);
                                        gsdbae.setSkill_detail(content);
                                    } else {
                                        System.out.println("格式不匹配，无法分割");
                                    }
                                    gsdbam.updateGodSkillDetailAfterAwakening(gsdbae);
                                }
                            } else if (gsdaam.consultSkillId(allGodId.get(idcount)) <= 0) {
                                JSONObject skillJson = data.getJSONObject(allGodId.get(idcount) + "" + (skillcount + 1));
                                GodSkillDetailAfterAwakeningEntity gsdaae = new GodSkillDetailAfterAwakeningEntity();
//                                System.out.println(allGodId.get(idcount));
                                gsdaae.setGod_id(allGodId.get(idcount));
                                gsdaae.setSkill_id(Integer.valueOf(allGodId.get(idcount) + "" + (skillcount + 1)));
                                gsdaae.setSkill_name(skillJson.getString("name"));
                                gsdaae.setSkill_icon(skillJson.getString("icon"));
                                gsdaae.setSkill_detail(skillJson.getString("normaldesc"));
                                if (data.getString("add") != null) {
                                    gsdaae.setSkill_add(data.getString("add"));
                                }

                                if (data.getString("add_type") != null) {
                                    gsdaae.setSkill_add_type(Integer.parseInt(data.getString("add_type")));
                                }
                                gsdaam.addSkillBasicBeforeAwakening(gsdaae);
//                                System.out.println("调用add");
                                List<String> desc = skillJson.getList("desc", String.class);
                                for (String s : desc) {
                                    GodSkillDetailBeforeAwakeningEntity gsdbae = new GodSkillDetailBeforeAwakeningEntity();
                                    gsdbae.setGod_id(allGodId.get(idcount));
                                    gsdbae.setSkill_id(Integer.valueOf(allGodId.get(idcount) + "" + (skillcount + 1)));
                                    String regex = "^Lv.(\\d+)(.*)"; // 正则表达式

                                    Pattern pattern = Pattern.compile(regex);
                                    Matcher matcher = pattern.matcher(s);
                                    if (matcher.matches()) {
                                        String lvPart = matcher.group(1); // 提取 "Lv.数字"
                                        String content = matcher.group(2).trim(); // 提取后面的内容并去除首尾空格
                                        gsdbae.setSkill_level(lvPart);
                                        gsdbae.setSkill_detail(content);
                                    } else {
                                        System.out.println("格式不匹配，无法分割");
                                    }
                                    gsdbam.updateGodSkillDetailAfterAwakening(gsdbae);
                                }
                            }
                        } else {
                            GodSkillDetailAfterAwakeningEntity gsdaae = new GodSkillDetailAfterAwakeningEntity();
                            if (gsdaam.consultGodId(allGodId.get(idcount)) <= 0) {
                                gsdaae.setGod_id(allGodId.get(idcount));
                                if (data.getString("add") != null) {
                                    gsdaae.setSkill_add(data.getString("add"));
                                }
                                if (data.getString("add_type") != null) {
                                    gsdaae.setSkill_add_type(Integer.parseInt(data.getString("add_type")));
                                }
                                gsdaam.addSkillBasicBeforeAwakening(gsdaae);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取式神觉醒前后的基本属性
     */
    public void acquisditon_god_data() {
        //觉醒前
        String url = "https://g37simulator.webapp.163.com/get_hero_attr?awake=0&level=1&star=2&heroid=";
        List<Integer> god_ids = gbim.allGodId();
        for (Integer god_id : god_ids) {
            if (gdaam.consultId(god_id) <= 0) {
                GodDataAfterAwakeningEntity gdaae = new GodDataAfterAwakeningEntity();
                try {
                    JSONObject jsonObject = httpUtils.sendGetRequest(url + god_id);
                    //式神ID
                    gdaae.setGod_id(god_id);
                    JSONObject data = jsonObject.getJSONObject("data");
                    //awakeitem（觉醒材料）
                    gdaae.setAwakeitem(data.getString("awakeitem"));
                    //critPower（暴击伤害）
                    gdaae.setCritpower(data.getBigDecimal("critPower"));
                    //bodyicon（身体图标）
                    gdaae.setBodyicon(data.getString("bodyicon"));
                    //initTurnPos（初始出手顺位）
                    gdaae.setInitturnpos(data.getBigDecimal("initTurnPos"));
                    //debuffEnhance（减益强化）
                    gdaae.setDebuffenhance(data.getBigDecimal("debuffEnhance"));
                    //defense（防御）
                    gdaae.setDefense(data.getBigDecimal("defense"));
                    //speed（速度）
                    gdaae.setSpeed(data.getBigDecimal("speed"));
                    //hurtReboundRate（伤害反弹率）
                    gdaae.setHurtreboundrate(data.getBigDecimal("hurtReboundRate"));
                    //hurtAdditionRate（伤害加成率）
                    gdaae.setHurtadditionrate(data.getBigDecimal("hurtAdditionRate"));
                    //headicon（头像图标）
                    gdaae.setHeadicon(data.getString("headicon"));
                    //attack（攻击）
                    gdaae.setAttack(data.getBigDecimal("attack"));
                    //score（评分）
                    gdaae.setScore(data.getString("score"));
                    //maxHp（最大生命值）
                    gdaae.setMaxhp(data.getBigDecimal("maxHp"));
                    //debuffResist（减益抵抗）
                    gdaae.setDebuffresist(data.getBigDecimal("debuffResist"));
                    //dodge（闪避）
                    gdaae.setDodge(data.getBigDecimal("dodge"));
                    //debuffReflect（减益反射）
                    gdaae.setDebuffreflect(data.getInteger("debuffReflect"));
                    //revenge（反击）
                    gdaae.setRevenge(data.getBigDecimal("revenge"));
                    //curedStrenthRate（受治疗强度率）
                    gdaae.setCuredstrenthrate(data.getBigDecimal("curedStrenthRate"));
                    //icon（图标）
                    gdaae.setIcon(data.getString("icon"));
                    //leechRate（吸血率）
                    gdaae.setLeechrate(data.getBigDecimal("leechRate"));
                    //koGainHPRate（击杀回血率）
                    gdaae.setKogainhprate(data.getBigDecimal("koGainHPRate"));
                    //critRate（暴击率）
                    gdaae.setCritrate(data.getBigDecimal("critRate"));
                    //cureStrenthRate（治疗强度率）
                    gdaae.setCurestrenthrate(data.getBigDecimal("cureStrenthRate"));
                    //koGainMP（击杀回蓝）
                    gdaae.setKogainmp(data.getInteger("koGainMP"));
                    //hurtReductionRate (伤害减免率）
                    gdaae.setHurtreductionrate(data.getBigDecimal("hurtReductionRate"));
                    if (gdaam.addGodData(gdaae) > 0) {
                        logger.info(god_id + "添加成功");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (gdbam.consultId(god_id) <= 0) {
                GodDataBeforeAwakeningEntity gdbae = new GodDataBeforeAwakeningEntity();
                try {
                    JSONObject jsonObject = httpUtils.sendGetRequest(url + god_id);
                    //式神ID
                    gdbae.setGod_id(god_id);
                    JSONObject data = jsonObject.getJSONObject("data");
                    //awakeitem（觉醒材料）
                    gdbae.setAwakeitem(data.getString("awakeitem"));
                    //critPower（暴击伤害）
                    gdbae.setCritpower(data.getBigDecimal("critPower"));
                    //bodyicon（身体图标）
                    gdbae.setBodyicon(data.getString("bodyicon"));
                    //initTurnPos（初始出手顺位）
                    gdbae.setInitturnpos(data.getBigDecimal("initTurnPos"));
                    //debuffEnhance（减益强化）
                    gdbae.setDebuffenhance(data.getBigDecimal("debuffEnhance"));
                    //defense（防御）
                    gdbae.setDefense(data.getBigDecimal("defense"));
                    //speed（速度）
                    gdbae.setSpeed(data.getBigDecimal("speed"));
                    //hurtReboundRate（伤害反弹率）
                    gdbae.setHurtreboundrate(data.getBigDecimal("hurtReboundRate"));
                    //hurtAdditionRate（伤害加成率）
                    gdbae.setHurtadditionrate(data.getBigDecimal("hurtAdditionRate"));
                    //headicon（头像图标）
                    gdbae.setHeadicon(data.getString("headicon"));
                    //attack（攻击）
                    gdbae.setAttack(data.getBigDecimal("attack"));
                    //score（评分）
                    gdbae.setScore(data.getString("score"));
                    //maxHp（最大生命值）
                    gdbae.setMaxhp(data.getBigDecimal("maxHp"));
                    //debuffResist（减益抵抗）
                    gdbae.setDebuffresist(data.getBigDecimal("debuffResist"));
                    //dodge（闪避）
                    gdbae.setDodge(data.getBigDecimal("dodge"));
                    //debuffReflect（减益反射）
                    gdbae.setDebuffreflect(data.getInteger("debuffReflect"));
                    //revenge（反击）
                    gdbae.setRevenge(data.getBigDecimal("revenge"));
                    //curedStrenthRate（受治疗强度率）
                    gdbae.setCuredstrenthrate(data.getBigDecimal("curedStrenthRate"));
                    //icon（图标）
                    gdbae.setIcon(data.getString("icon"));
                    //leechRate（吸血率）
                    gdbae.setLeechrate(data.getBigDecimal("leechRate"));
                    //koGainHPRate（击杀回血率）
                    gdbae.setKogainhprate(data.getBigDecimal("koGainHPRate"));
                    //critRate（暴击率）
                    gdbae.setCritrate(data.getBigDecimal("critRate"));
                    //cureStrenthRate（治疗强度率）
                    gdbae.setCurestrenthrate(data.getBigDecimal("cureStrenthRate"));
                    //koGainMP（击杀回蓝）
                    gdbae.setKogainmp(data.getInteger("koGainMP"));
                    //hurtReductionRate (伤害减免率）
                    gdbae.setHurtreductionrate(data.getBigDecimal("hurtReductionRate"));
                    if (gdbam.addGodData(gdbae) > 0) {
                        logger.info(god_id + "添加成功");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}