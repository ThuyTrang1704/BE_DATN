package com.example.toeicwebsite.data.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class AnswerDTO {
    private Long id;
    private String content;
    private boolean correctAnswer;
    private Long questionId;
}
