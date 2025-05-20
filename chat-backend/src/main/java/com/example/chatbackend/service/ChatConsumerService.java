package com.example.chatbackend.service;

import com.example.chatbackend.component.ChatWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatConsumerService {
    private final ChatWebSocketHandler chatWebSocketHandler;

    @KafkaListener(topics = "topic-chat", groupId = "chat")
    public void listen(String message) {
        System.out.println("Received: " + message);
        chatWebSocketHandler.broadcast(message);
    }
}