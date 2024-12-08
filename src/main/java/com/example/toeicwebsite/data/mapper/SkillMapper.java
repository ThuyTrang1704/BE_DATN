package com.example.toeicwebsite.data.mapper;

import com.example.toeicwebsite.data.dto.SkillDTO;
import com.example.toeicwebsite.data.entity.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    Skill toEntity(SkillDTO skillDTO);

    SkillDTO toDTO(Skill skill);
}
