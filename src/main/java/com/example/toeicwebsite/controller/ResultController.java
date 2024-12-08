package com.example.toeicwebsite.controller;

import com.example.toeicwebsite.data.dto.ResultInputDTO;
import com.example.toeicwebsite.data.dto.SkillDTO;
import com.example.toeicwebsite.service.ResultService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ResultController {
    @Autowired
    private ResultService resultService;

    @SecurityRequirement(name = "Bearer Authentication")
//    @PreAuthorize("isAuthenticated()")
    @PostMapping("/saveResult")
    public ResponseEntity<?> saveResult(@Valid @RequestBody ResultInputDTO resultInputDTO) {
        return ResponseEntity.ok(resultService.saveResult(resultInputDTO));
    }

    @SecurityRequirement(name = "Bearer Authentication")
//    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/filterUserResult")
    public ResponseEntity<?> filterUserResult(@RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "10") int pageSize,
                                         @RequestParam(defaultValue = "") String keyword) {

        return ResponseEntity.ok(resultService.filterResult(keyword, pageNumber, pageSize));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/countResult")
    public ResponseEntity<?> countResult() {
        return ResponseEntity.ok(resultService.countResult());
    }
}
