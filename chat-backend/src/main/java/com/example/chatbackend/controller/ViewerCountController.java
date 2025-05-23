package com.example.chatbackend.controller;

import com.example.chatbackend.component.ChatWebSocketHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewerCountController {

    private final ChatWebSocketHandler chatWebSocketHandler;

    public ViewerCountController(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    @GetMapping("/viewer-count")
    public int getViewerCount() {
        return chatWebSocketHandler.getViewerCount();
    }
}
