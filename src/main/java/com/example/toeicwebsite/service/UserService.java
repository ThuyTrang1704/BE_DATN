package com.example.toeicwebsite.service;

import com.example.toeicwebsite.data.dto.*;
import com.example.toeicwebsite.data.entity.User;

import java.util.Optional;

public interface UserService {
    JwtResponseDTO loginUser(LoginDTO loginDTO);

    MessageResponse createRegister(RegisterDTO registerDTO);

    MessageResponse updateNguoiDung(UserDTO userDTO);

    UserDTO getNguoiDungHienTai();

    PaginationDTO filterSkill(String keyword, int pageNumber, int pageSize);

    long countUsersExcludingAdmin();

//    void createPasswordResetTokenForUser(String token);
//
//    void sendPasswordResetEmail(User user, String token);
//
//    void resetPassword(String token, String newPassword);
//
//    User getUserByEmail(String email);
}
