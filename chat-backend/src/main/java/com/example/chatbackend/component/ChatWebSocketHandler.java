package com.example.chatbackend.component;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final String REDIS_VIEWER_COUNT_KEY = "viewer:count";
    private final StringRedisTemplate stringRedisTemplate;
    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    public ChatWebSocketHandler(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);

        stringRedisTemplate.opsForValue().increment(REDIS_VIEWER_COUNT_KEY);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {

        if(sessions.remove(session)) {
            Long currentCount = stringRedisTemplate.opsForValue().decrement(REDIS_VIEWER_COUNT_KEY);

            if(currentCount < 0) {
                stringRedisTemplate.opsForValue().set(REDIS_VIEWER_COUNT_KEY, "0");
                currentCount= 0L;
            }
        }
    }

    public void broadcast(String message) {
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Long getViewerCount() {
        String count = stringRedisTemplate.opsForValue().get(REDIS_VIEWER_COUNT_KEY);
        return count != null ? Long.parseLong(count) : 0L;
    }

    // 방송 시작/종료 시 카운트 초기화
    public void resetViewerCount() {
        stringRedisTemplate.opsForValue().set(REDIS_VIEWER_COUNT_KEY, "0");
    }

    public void closeAllSessions() {
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                try {
                    session.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            sessions.clear();
        }
    }

}
