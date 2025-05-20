package com.example.chatbackend.service;

import com.example.chatbackend.domain.ChatMessage;
import com.example.chatbackend.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatDBService {
    private final ChatMessageRepository chatMessageRepository;

    @KafkaListener(topics = "topic-chat", groupId = "chat-db")
    public void listen(String message) {

        ChatMessage chatMessage = new ChatMessage(message);

        chatMessageRepository.save(chatMessage);
        System.out.println("메시지가 MongoDB에 저장되었습니다: " + message);
    }
}
