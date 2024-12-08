package com.example.toeicwebsite.data.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class PartDTO {
    private Long id;
    private String name;
    private String description;
    private int part_number;
    private long skillId;
}
