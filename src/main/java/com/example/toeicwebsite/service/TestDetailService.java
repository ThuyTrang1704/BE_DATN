package com.example.toeicwebsite.service;

import com.example.toeicwebsite.data.dto.MessageResponse;


public interface TestDetailService {
    MessageResponse saveListTestDetail(Long structureId, Long testId);

    MessageResponse saveTestDetail(Long kindOfStructureId, Long testId);
}
