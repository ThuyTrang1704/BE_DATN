package com.example.toeicwebsite.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MessageResponse {
    private int httpCode;
    private String message;
}
