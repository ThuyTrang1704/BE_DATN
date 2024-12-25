package com.example.toeicwebsite.controller;

import com.example.toeicwebsite.data.dto.ChangePasswordRequest;
import com.example.toeicwebsite.data.dto.MessageResponse;
import com.example.toeicwebsite.data.dto.UserDTO;
import com.example.toeicwebsite.data.repository.UserRepository;
import com.example.toeicwebsite.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/updateNguoiDung")
    public ResponseEntity<?> updateNguoiDung(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateNguoiDung(userDTO));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/change-password")
    public ResponseEntity<MessageResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        // Gọi service để thay đổi mật khẩu và nhận phản hồi
        MessageResponse response = userService.changePassword(
                request.getEmail(),
                request.getOldPassword(),
                request.getNewPassword());
        // Trả về ResponseEntity với mã HTTP và MessageResponse
        return ResponseEntity.status(response.getHttpCode()).body(response);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/profile")
    public ResponseEntity<?> getNguoiDungHienTai() {
        return ResponseEntity.ok(userService.getNguoiDungHienTai());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        userService.sendResetPasswordToken(email);
        return ResponseEntity.ok("Reset password link has been sent to your email");
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token,
                                           @RequestParam String email,
                                           @RequestParam String newPassword) {
        userService.resetPassword(token, email, newPassword);
        return ResponseEntity.ok("Password has been reset successfully");
    }



    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/filterUser")
    public ResponseEntity<?> filterUser(@RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "10") int pageSize,
                                         @RequestParam(defaultValue = "") String keyword) {

        return ResponseEntity.ok(userService.filterSkill(keyword, pageNumber, pageSize));
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/countUsers")
    public ResponseEntity<Long> countUsers() {
        return ResponseEntity.ok(userService.countUsersExcludingAdmin());
    }


}
