package com.example.chatbackend.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "chatMessages")
@Data
@NoArgsConstructor
public class ChatMessage {
    @Id
    private String id;
    private String content;
    private Date timestamp;

    public ChatMessage(String content) {
        this.content = content;
        this.timestamp = new Date();
    }
}
