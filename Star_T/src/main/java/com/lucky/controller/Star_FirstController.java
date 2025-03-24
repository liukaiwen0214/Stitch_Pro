package com.lucky.controller;

import com.lucky.entity.UserEntity;
import com.lucky.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class Star_FirstController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        UserEntity user = userService.selectUserEntityByEmail(email);
        if (user != null) {
            if (user.getUser_password().equals(password)) {
                session.setAttribute("user", email); // 将邮箱存入Session
                result.put("success", true);
                result.put("redirectUrl", "/Star_Home");
            } else {
                result.put("success", false);
                result.put("errorMsg", "密码错误");
            }
        } else {
            result.put("success", false);
            result.put("errorMsg", "邮箱或密码错误");
        }
        return result;
    }
    @RequestMapping("/Star_Home")
    public String showHomePage() {
        return "/Star_Home"; // 对应视图解析器配置的 JSP 文件名
    }


}
