package com.example.toeicwebsite.service;

import com.example.toeicwebsite.data.dto.MessageResponse;
import com.example.toeicwebsite.data.dto.PaginationDTO;
import com.example.toeicwebsite.data.dto.QuestionDTO;

import java.util.List;

public interface QuestionService {
    List<QuestionDTO> getAllQuestionsWithAnswersByTopicId(Long topicId);

    QuestionDTO getQuestionById(Long questionId);

    List<Long> getAllQuestionsIDByTopicId(Long topicId);

    PaginationDTO filterQuestion(String keyword, int pageNumber, int pageSize);

    MessageResponse createQuestion(QuestionDTO questionDTO);

    MessageResponse deleteQuestion(Long questionId);
}
