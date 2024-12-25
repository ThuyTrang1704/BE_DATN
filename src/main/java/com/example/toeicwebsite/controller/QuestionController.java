package com.example.toeicwebsite.controller;

import com.example.toeicwebsite.data.dto.QuestionDTO;
import com.example.toeicwebsite.service.QuestionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/{topicId}")
    public ResponseEntity<?> getAllQuestionsWithAnswersByTopicId(@PathVariable Long topicId) {
        return ResponseEntity.ok(questionService.getAllQuestionsWithAnswersByTopicId(topicId));
    }

    @GetMapping("/oneQuestion/{questionId}")
    public ResponseEntity<?> getQuestionWithId(@PathVariable Long questionId) {
        return ResponseEntity.ok(questionService.getQuestionById(questionId));
    }
    //Test service for get all questions id by topic id
    @GetMapping("/listid/{topicId}")
    public ResponseEntity<?> getAllQuestionsIdByTopicId(@PathVariable Long topicId) {
        return ResponseEntity.ok(questionService.getAllQuestionsIDByTopicId(topicId));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/filterQuestion")
    public ResponseEntity<?> filterQuestion(@RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "10") int pageSize,
                                         @RequestParam(defaultValue = "") String keyword) {

        return ResponseEntity.ok(questionService.filterQuestion(keyword, pageNumber, pageSize));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @PostMapping("/createQuestion")
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionDTO questionDTO) {
        return ResponseEntity.ok(questionService.createQuestion(questionDTO));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @DeleteMapping ("/deleteQuestion")
    public ResponseEntity<?> deletePart(@RequestParam Long questionId) {
        return ResponseEntity.ok(questionService.deleteQuestion(questionId));
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @PostMapping("/uploadQuestions")
    public ResponseEntity<?> uploadQuestions(@RequestParam("file") MultipartFile file) {
        try {
            questionService.importQuestionsFromExcel(file);
            return ResponseEntity.ok("Nhập câu hỏi thành công.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi nhập câu hỏi: " + e.getMessage());
        }
    }





}
