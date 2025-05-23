package com.example.chatbackend.service;

import com.example.chatbackend.dto.MessageRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(MessageRequestDto requestDto) {
        try {
            String json = objectMapper.writeValueAsString(requestDto);
            kafkaTemplate.send("topic-chat", json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
