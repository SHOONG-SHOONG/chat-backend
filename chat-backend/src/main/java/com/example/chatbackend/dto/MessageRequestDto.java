package com.example.chatbackend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class MessageRequestDto {
    private String name;
    private String content;
}
