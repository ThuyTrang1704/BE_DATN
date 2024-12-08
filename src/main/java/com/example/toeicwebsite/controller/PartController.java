package com.example.toeicwebsite.controller;

import com.example.toeicwebsite.data.dto.PartDTO;
import com.example.toeicwebsite.data.dto.SkillDTO;
import com.example.toeicwebsite.data.mapper.PartMapper;
import com.example.toeicwebsite.service.PartService;
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
public class PartController {
    @Autowired
    private PartService partService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @PostMapping("/createPart")
    public ResponseEntity<?> createPart(@Valid @RequestBody PartDTO partDTO) {
        return ResponseEntity.ok(partService.createPart(partDTO));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/filterPart")
    public ResponseEntity<?> filterPart(@RequestParam(defaultValue = "0") int pageNumber,
                                              @RequestParam(defaultValue = "10") int pageSize,
                                              @RequestParam(defaultValue = "") String keyword) {

        return ResponseEntity.ok(partService.filterPart(keyword, pageNumber, pageSize));
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @DeleteMapping ("/deletePart")
    public ResponseEntity<?> deletePart(@RequestParam Long partId) {
        return ResponseEntity.ok(partService.deletePart(partId));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @PutMapping ("/updatePart")
    public ResponseEntity<?> updatePart(@Valid @RequestBody PartDTO partDTO) {
        return ResponseEntity.ok(partService.updatePart(partDTO));
    }
}
