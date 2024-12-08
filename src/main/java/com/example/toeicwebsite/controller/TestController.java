package com.example.toeicwebsite.controller;

import com.example.toeicwebsite.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private TestService testService;

    @PostMapping("/addTest")
    public ResponseEntity<?> addTestStructureDetails(@RequestParam Long kindOfStructureId) {
        return ResponseEntity.ok(testService.saveTest(kindOfStructureId));
    }
}
