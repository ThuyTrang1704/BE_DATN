package com.example.toeicwebsite.data.mapper;

import com.example.toeicwebsite.data.dto.LevelDTO;
import com.example.toeicwebsite.data.dto.SkillDTO;
import com.example.toeicwebsite.data.entity.Level;
import com.example.toeicwebsite.data.entity.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LevelMapper {
    Level toEntity(LevelDTO levelDTO);

    LevelDTO toDTO(Level level);
}
