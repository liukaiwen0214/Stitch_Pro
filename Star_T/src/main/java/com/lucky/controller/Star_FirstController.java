package com.lucky.controller;


import com.lucky.entity.GodEntity;
import com.lucky.service.GodService;
import com.lucky.service.UserService;
import com.lucky.util.AiChat;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Star_FirstController {
    @Autowired
    private UserService userService;

    @Autowired
    private GodService godService;

    // 登录接口
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     HttpSession session) {
        Map<String, Object> result = new HashMap<>();

        if (userService.loginValidate(email, password)) {
            session.setAttribute("user", email);
            result.put("code", 200);
            result.put("message", "登录成功");
            result.put("redirectUrl", "/Star_Home");
        } else {
            result.put("code", 401);
            result.put("message", "用户名或密码错误");
        }
        return result;
    }

    // 获取当前用户
    @GetMapping("/currentUser")
    @ResponseBody
    public Map<String, Object> getCurrentUser(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Object user = session.getAttribute("user");
        if (user != null) {
            result.put("code", 200);
            result.put("data", user);
        } else {
            result.put("code", 401);
        }
        return result;
    }

    // 退出登录
    @PostMapping("/logout")
    @ResponseBody
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate();
        return Collections.singletonMap("code", 200);
    }


    @RequestMapping("/Star_Home")
    public String showHomePage(HttpSession session, Model model) {
        Object user = session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "/Star_Home"; // 对应视图解析器配置的 JSP 文件名
    }

    @RequestMapping("/home_context")
    public String showHome_context(Model model) {
        return "/Star_Context";
    }
    @RequestMapping("/other")
    public String other() {
        return "/other";
    }

    @RequestMapping("/getGodCount")
    public String countCharactersByRarity(Model model) {
        List<Map<String, Object>> charactersByRarity = godService.getGodCount();
        model.addAttribute("charactersByRarity", charactersByRarity);
        return "/Star_Context";
    }

    @PostMapping("/chat")
    @ResponseBody
    public Map<String, String> handleChatMessage(@RequestBody Map<String, String> request) {
        AiChat aiChat = new AiChat();
        String message = request.get("message");
        List<String> responsived = aiChat.responsive(message);
        Map<String, String> response = new HashMap<>();
        for (String s : responsived) {
            response.put("reply", s);
        }
        return response;
    }
    @GetMapping("/getRandomGod")
    @ResponseBody
    public GodEntity getRandomGod(){
        return godService.getRandomGod();
    }
}
