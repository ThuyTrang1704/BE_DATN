package com.example.toeicwebsite.data.mapper;

import com.example.toeicwebsite.data.dto.PartDTO;
import com.example.toeicwebsite.data.dto.PartShowDTO;
import com.example.toeicwebsite.data.entity.Part;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartMapper {
    Part toEntity(PartDTO partDTO);

    @Mapping(source = "skill",target = "skillDTO")
    PartShowDTO toDTO(Part part);
}
