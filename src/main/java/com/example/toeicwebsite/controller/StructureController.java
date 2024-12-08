package com.example.toeicwebsite.controller;

import com.example.toeicwebsite.data.dto.StructureDTO;
import com.example.toeicwebsite.service.StructureService;
import com.example.toeicwebsite.service.TopicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class StructureController {
    @Autowired
    private StructureService structureService;


    @PostMapping("/saveStructure")
    public ResponseEntity<?> saveStructure(@Valid @RequestBody List<StructureDTO> structureDTOs) {
        return ResponseEntity.ok(structureService.saveStructure(structureDTOs));
    }


    @GetMapping("/ExamTest/{structureId}")
    public ResponseEntity<?> getStructureByStructureId(@PathVariable Long structureId) {
        return ResponseEntity.ok(structureService.getStructureByKindStructureId(structureId));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/countStructure")
    public ResponseEntity<Long> countStructure() {
        return ResponseEntity.ok(structureService.countStructureCreate());
    }
}
