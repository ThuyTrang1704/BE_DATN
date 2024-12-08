package com.example.toeicwebsite.data.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class RegisterDTO {
    @NotNull(message = "ten dang nhap khong duoc de trong")
    @NotEmpty(message = "ten dang nhap khong duoc de trong")
    private String name;

    @Email(message = "email khong dung dinh dang")
    @NotNull(message = "email khong duoc de trong")
    @NotEmpty(message = "email khong duoc de trong")
    private String email;

    @NotNull(message = "password khong duoc de trong")
    @NotEmpty(message = "password khong duoc de trong")
    private String password;

    @NotNull(message = "passwordConfirm khong duoc de trong")
    private String passwordConfirm;


//    @NotNull(message = "Ban nen nho chon Role")

    private long roleId;
}
