package com.example.toeicwebsite.service;

import com.example.toeicwebsite.data.dto.MessageResponse;
import com.example.toeicwebsite.data.dto.PaginationDTO;
import com.example.toeicwebsite.data.dto.ResultDTO;
import com.example.toeicwebsite.data.dto.ResultInputDTO;

public interface ResultService {
    MessageResponse saveResult(ResultInputDTO resultInputDTO);

    PaginationDTO filterResult(String keyword, int pageNumber, int pageSize);

//    PaginationDTO filterResult(String keyword, Long userId, int pageNumber, int pageSize);

    long countResult();
}
