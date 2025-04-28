package com.example.chatbackend.controller;

import com.example.chatbackend.service.ChatProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {
    private final ChatProducerService chatProducerService;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        System.out.println("Received param: " + message);  // 확인용
        if (message == null) {
            return "No message received.";
        }
        chatProducerService.sendMessage(message);
        return "Message sent: " + message;
    }
}