package com.example.toeicwebsite.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorMessageResponseDTO {
    private String message;
    private Object errors;
    private String path;
}
