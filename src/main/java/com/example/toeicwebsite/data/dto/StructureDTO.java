package com.example.toeicwebsite.data.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class StructureDTO {
    private Long id;
    private String name;
    private int number_of_topic;
    private String level_of_topic;
    private Long part_id;

    List<TopicDTO> topics;
}
