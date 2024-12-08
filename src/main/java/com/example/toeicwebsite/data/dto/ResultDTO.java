package com.example.toeicwebsite.data.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ResultDTO {
    private Long id;
    private String totalMark;
    private String status;
    private Date createAt;
    private Long structureId;

    private UserDTO user;
}
