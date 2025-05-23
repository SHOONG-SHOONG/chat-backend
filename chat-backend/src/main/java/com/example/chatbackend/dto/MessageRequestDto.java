package com.example.chatbackend.dto;

import lombok.Data;

@Data
public class MessageRequestDto {
    private String name;
    private String content;
}
