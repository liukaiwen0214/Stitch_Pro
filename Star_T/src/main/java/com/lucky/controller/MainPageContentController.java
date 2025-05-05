package com.lucky.controller;

import com.lucky.util.AiChat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 */
@Controller("/MainPage")
public class MainPageContentController {

    @RequestMapping("/MainPageContent")
    public String showHome_context() {
        return "/MainPageContent";
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
}
