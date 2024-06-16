package com.trifonov.chat.controller;


import com.trifonov.chat.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.newUser")
    @SendTo("/topic/public")
    public ChatMessage newUser(ChatMessage chatMessage) {
        chatMessage.setContent("New user joined: " + chatMessage.getSender());
        return chatMessage;
    }
}

