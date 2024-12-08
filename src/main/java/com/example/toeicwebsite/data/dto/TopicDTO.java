package com.example.toeicwebsite.data.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class TopicDTO {
    private Long id;
    private String name;
    private String content;

    private String imageName;
    private String audioName;

    private String pathImage;
    private String pathAudio;

    private Long partId;
    private Long levelId;

    private boolean isDeleted;

    List<QuestionDTO> questions;
}
