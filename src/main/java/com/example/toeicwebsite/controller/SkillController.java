package com.example.toeicwebsite.controller;

import com.example.toeicwebsite.data.dto.SkillDTO;
import com.example.toeicwebsite.service.SkillService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @PostMapping("/createSkill")
    public ResponseEntity<?> createSkill(@Valid @RequestBody SkillDTO skillDTO) {
        return ResponseEntity.ok(skillService.createSkill(skillDTO));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/filterSkill")
    public ResponseEntity<?> filterSkill(@RequestParam(defaultValue = "0") int pageNumber,
                                              @RequestParam(defaultValue = "10") int pageSize,
                                              @RequestParam(defaultValue = "") String keyword) {

        return ResponseEntity.ok(skillService.filterSkill(keyword, pageNumber, pageSize));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @PutMapping("/updateSkill")
    public ResponseEntity<?> updateSkill(@Valid @RequestBody SkillDTO skillDTO) {
        return ResponseEntity.ok(skillService.updateSkill(skillDTO));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @DeleteMapping ("/deleteSkill")
    public ResponseEntity<?> deleteSkill(@RequestParam Long skillId) {
        return ResponseEntity.ok(skillService.deleteSkill(skillId));
    }
}
