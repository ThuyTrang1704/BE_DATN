package com.example.toeicwebsite.service;

import com.example.toeicwebsite.data.dto.StructureDTO;

import java.util.List;

public interface StructureService {
    Long saveStructure(List<StructureDTO> structures);

    List<StructureDTO> getStructureByKindStructureId(Long kindStructureId);

    List<Long> getListStructureIDByKindStructure(Long kindStructureId);

    long countStructureCreate();
}
