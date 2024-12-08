package com.example.toeicwebsite.controller;

import com.example.toeicwebsite.service.TestDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/testDetail")
public class TestDetailController {
    @Autowired
    private TestDetailService testDetailService;

    @PostMapping("/save")
    public ResponseEntity<?> saveTestDetail(@RequestParam Long structureId, @RequestParam Long testId) {
        return ResponseEntity.ok(testDetailService.saveListTestDetail(structureId, testId));
    }

    @PostMapping("/saveList")
    public ResponseEntity<?> saveListTestDetail(@RequestParam Long kindOfStructureId, @RequestParam Long testId) {
        return ResponseEntity.ok(testDetailService.saveTestDetail(kindOfStructureId, testId));
    }
}
