package com.example.toeicwebsite.controller;


import com.example.toeicwebsite.data.dto.RegisterDTO;
import com.example.toeicwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/register")
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {


        return ResponseEntity.ok(userService.createRegister(registerDTO));
    }
}
