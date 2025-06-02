package com.example.chatbackend.service;

import com.example.chatbackend.domain.ChatMessage;
import com.example.chatbackend.dto.MessageRequestDto;
import com.example.chatbackend.repository.ChatMessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatDBService {
    private final ChatMessageRepository chatMessageRepository;

    @KafkaListener(topics = "topic-chat", groupId = "chat-db")
    public void listen(String message) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        MessageRequestDto requestDto = mapper.readValue(message, MessageRequestDto.class);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUserName(requestDto.getName());
        chatMessage.setContent(requestDto.getContent());
        chatMessage.setTimestamp(LocalDateTime.now());

        chatMessageRepository.save(chatMessage);
        System.out.println("메시지가 MongoDB에 저장되었습니다: " + message);

        // 로그 필드 세팅
        MDC.put("eventType", "CHAT_SAVED");
        MDC.put("name", requestDto.getName());
        MDC.put("content", requestDto.getContent());
        MDC.put("timestamp", Instant.now().toString());

        log.info("메시지가 MongoDB에 저장되었습니다");

        MDC.clear();
    }
}
