package com.example.chatbackend.controller;

import com.example.chatbackend.dto.MessageRequestDto;
import com.example.chatbackend.service.ChatProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {
    private final ChatProducerService chatProducerService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequestDto requestDto) {
        chatProducerService.sendMessage(requestDto);
        return ResponseEntity.ok("Message sent");
    }
}