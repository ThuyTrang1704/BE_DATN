package com.example.toeicwebsite.data.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
//    private String password;


}
