package com.lucky.util;

import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionChoice;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;


import java.util.ArrayList;
import java.util.List;

public class AiChat {
    public static void main(String[] args) {
        AiChat aiChat = new AiChat();
        List<String> responsived = aiChat.responsive("你是谁");
        for (String s : responsived) {
            System.out.println(s);
        }
    }
    public  List<String> responsive(String concern) {
        // 从环境变量中获取API密钥
        String apiKey = "4e8f8190-4594-40da-9824-7130a363e32f";

        // 创建ArkService实例
        ArkService arkService = ArkService.builder().apiKey(apiKey).build();

        // 初始化消息列表
        List<ChatMessage> chatMessages = new ArrayList<>();

        // 创建用户消息
        ChatMessage userMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER) // 设置消息角色为用户
                .content(concern) // 设置消息内容
                .build();

        // 将用户消息添加到消息列表
        chatMessages.add(userMessage);

        // 创建聊天完成请求
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("doubao-1-5-lite-32k-250115")// 需要替换为Model ID
                .messages(chatMessages) // 设置消息列表
                .build();

        // 发送聊天完成请求并打印响应
        try {
            // 获取响应每个选择的消息内容
            List<ChatCompletionChoice> choiceList = arkService.createChatCompletion(chatCompletionRequest).getChoices();
            List<String> context = new ArrayList<>();
            for (ChatCompletionChoice choice : choiceList) {
                context.add((String) choice.getMessage().getContent());
            }
            return context;
        } catch (Exception e) {
            System.out.println("请求失败: " + e.getMessage());
        } finally {
            // 关闭服务执行器
            arkService.shutdownExecutor();
        }
        List<String> nulls = new ArrayList<>();
        nulls.add("空值");
        return nulls;
    }
}


