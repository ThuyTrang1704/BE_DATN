package com.example.toeicwebsite.data.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class TestDetailDTO {
    private Long id;
    private Long testId;
    private Long questionId;
}
