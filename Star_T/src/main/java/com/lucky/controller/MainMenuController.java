package com.lucky.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页菜单
 */
@Controller("/MainMenu")
public class MainMenuController {
    //    private final PossessGodService pgs;
    private final Logger logger = LoggerFactory.getLogger(MainMenuController.class.getName());

//    public MainMenuController(PossessGodService pgs) {
//        this.pgs = pgs;
//    }

    @RequestMapping("/MainMenu")
    public String showHomePage(HttpSession session, Model model) {
        Object User_name = session.getAttribute("User_name");
        model.addAttribute("User_name", User_name);
        logger.info("获取用户成功：{}", User_name);
        return "/MainMenu"; // 对应视图解析器配置的 JSP 文件名
    }
}
