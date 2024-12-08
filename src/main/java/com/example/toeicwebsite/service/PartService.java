package com.example.toeicwebsite.service;

import com.example.toeicwebsite.data.dto.MessageResponse;
import com.example.toeicwebsite.data.dto.PaginationDTO;
import com.example.toeicwebsite.data.dto.PartDTO;
import com.example.toeicwebsite.data.dto.SkillDTO;

public interface PartService {
    MessageResponse createPart(PartDTO partDTO);
    PaginationDTO filterPart(String keyword, int pageNumber, int pageSize);

    MessageResponse updatePart(PartDTO partDTO);

    MessageResponse deletePart(Long partId);
}
