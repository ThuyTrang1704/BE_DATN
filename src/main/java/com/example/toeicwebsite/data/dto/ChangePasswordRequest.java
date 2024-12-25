package com.example.toeicwebsite.data.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ChangePasswordRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
}
