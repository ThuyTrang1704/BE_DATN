package com.example.toeicwebsite.controller;

import com.example.toeicwebsite.data.dto.LevelDTO;
import com.example.toeicwebsite.data.dto.SkillDTO;
import com.example.toeicwebsite.service.LevelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class LevelController {
    @Autowired
    private LevelService levelService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @PostMapping("/createLevel")
    public ResponseEntity<?> createLevel(@Valid @RequestBody LevelDTO levelDTO) {
        return ResponseEntity.ok(levelService.createLevel(levelDTO));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/filterLevel")
    public ResponseEntity<?> filterNhaCungCap(@RequestParam(defaultValue = "0") int pageNumber,
                                              @RequestParam(defaultValue = "10") int pageSize,
                                              @RequestParam(defaultValue = "") String keyword) {

        return ResponseEntity.ok(levelService.filterLevel(keyword, pageNumber, pageSize));
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @PutMapping("/updateLevel")
    public ResponseEntity<?> updateLevel(@Valid @RequestBody LevelDTO levelDTO) {
        return ResponseEntity.ok(levelService.updateLevel(levelDTO));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @DeleteMapping ("/deleteLevel")
    public ResponseEntity<?> deleteLevel(@RequestParam Long levelId) {
        return ResponseEntity.ok(levelService.deleteLevel(levelId));
    }
}
