package com.example.toeicwebsite.data.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class QuestionDTO {
    private Long id;
    private String name;
    private Long topicId;
    private List<AnswerDTO> answers;
}
