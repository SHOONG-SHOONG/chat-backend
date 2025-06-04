package com.example.chatbackend.controller;

import com.example.chatbackend.component.ChatWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EndLiveController {

    private final ChatWebSocketHandler chatWebSocketHandler;

    @PostMapping("/endLive")
    public ResponseEntity<String> endLive() {
        chatWebSocketHandler.closeAllSessions();

        chatWebSocketHandler.resetViewerCount();

        return ResponseEntity.ok("방송이 종료되었고, 시청자 수가 초기화되었습니다.");
    }
}
