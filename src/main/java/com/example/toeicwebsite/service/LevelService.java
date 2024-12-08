package com.example.toeicwebsite.service;

import com.example.toeicwebsite.data.dto.LevelDTO;
import com.example.toeicwebsite.data.dto.MessageResponse;
import com.example.toeicwebsite.data.dto.PaginationDTO;
import com.example.toeicwebsite.data.dto.SkillDTO;

public interface LevelService {
    MessageResponse createLevel(LevelDTO levelDTO);
    PaginationDTO filterLevel(String keyword, int pageNumber, int pageSize);
    MessageResponse updateLevel(LevelDTO levelDTO);

    MessageResponse deleteLevel(Long skillId);
}
