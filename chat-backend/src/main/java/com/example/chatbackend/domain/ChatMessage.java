package com.example.chatbackend.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "chatMessages")
@Data
@NoArgsConstructor
public class ChatMessage {
    @Id
    private String id;
    private String userName;
    private String content;
    private LocalDateTime timestamp;
}
