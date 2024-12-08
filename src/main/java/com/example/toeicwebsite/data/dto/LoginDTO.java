package com.example.toeicwebsite.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {
    @Email(message = "email khong dung dinh dang")
    @NotNull(message = "email khong duoc de trong")
    private String email;

    @NotNull(message = "password khong duoc de trong")
    private String password;
}
