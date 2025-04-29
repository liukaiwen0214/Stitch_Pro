package com.lucky.controller;


import com.lucky.entity.AuthUsersEntity;
import com.lucky.entity.GodEntity;
import com.lucky.service.AuthUsersSerivce;
import com.lucky.service.GodService;
import com.lucky.util.AiChat;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@Controller
public class Star_FirstController {
    private final GodService godService;
    private final AuthUsersSerivce authUsersSerivce; // 声明为 final（不可变）
    // 构造函数注入（Spring 4.3+ 后无需显式 @Autowired，构造函数唯一时可省略）
    @Autowired // 非必需，仅在有多个构造函数时需要
    public Star_FirstController(GodService godService,AuthUsersSerivce authUsersSerivce) {
        this.godService = godService;
        this.authUsersSerivce = authUsersSerivce;
    }


    private final Logger logger = Logger.getLogger(Star_FirstController.class.getName());

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_REGEX = "^1[3-9]\\d{9}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    // 登录接口
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestParam("email") String auth_User,@RequestParam("password") String password, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        AuthUsersEntity authUsers = new AuthUsersEntity();

        if (EMAIL_PATTERN.matcher(auth_User).matches()) {
            authUsers.setUser_email(auth_User);
        } else if (PHONE_PATTERN.matcher(auth_User).matches()) {
            authUsers.setUser_iphone(auth_User);
        } else {
            authUsers.setUser_name(auth_User);
        }
        authUsers.setUser_password(password);
        if (authUsersSerivce.authenticateUser(authUsers)) {
            session.setAttribute("User_name", authUsers.getUser_name());
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
        Object user = session.getAttribute("User_name");
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
        Object User_name = session.getAttribute("User_name");
        System.out.println(User_name);
        model.addAttribute("User_name", User_name);
        return "/Star_Home"; // 对应视图解析器配置的 JSP 文件名
    }

    @RequestMapping("/home_context")
    public String showHome_context() {
        return "/Star_Context";
    }

    @RequestMapping("/other")
    public String other() {
        return "/other";
    }

    @GetMapping("/getGodCount")
    @ResponseBody
    public List<Map<String, Object>> countCharactersByRarity() {
        return godService.getGodCount();
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
    public GodEntity getRandomGod() {
        return godService.getRandomGod();
    }
}
