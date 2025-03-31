package com.lucky.controller;


import com.lucky.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class Star_FirstController {
    @Autowired
    private UserService userService;

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
    public String showHomePage() {
        return "/Star_Home"; // 对应视图解析器配置的 JSP 文件名
    }
}
