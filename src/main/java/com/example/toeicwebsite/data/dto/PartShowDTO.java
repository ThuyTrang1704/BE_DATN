package com.example.toeicwebsite.data.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class PartShowDTO {
    private Long id;
    private String name;
    private String description;
    private int part_number;
    private SkillDTO skillDTO;
}
