package com.example.toeicwebsite.service.impl;

import com.example.toeicwebsite.data.dto.MessageResponse;
import com.example.toeicwebsite.data.dto.PaginationDTO;
import com.example.toeicwebsite.data.dto.SkillDTO;
import com.example.toeicwebsite.data.entity.Part;
import com.example.toeicwebsite.data.entity.Skill;
import com.example.toeicwebsite.data.entity.Topic;
import com.example.toeicwebsite.data.mapper.SkillMapper;
import com.example.toeicwebsite.data.repository.PartRepository;
import com.example.toeicwebsite.data.repository.SkillRepository;
import com.example.toeicwebsite.data.repository.TopicRepository;
import com.example.toeicwebsite.exception.ConflictException;
import com.example.toeicwebsite.exception.ResourceNotFoundException;
import com.example.toeicwebsite.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private SkillMapper skillMapper;
    @Autowired
    private PartRepository partRepository;
    @Override
    public MessageResponse createSkill(SkillDTO skillDTO) {
        if (skillRepository.findByName(skillDTO.getName()).isPresent()) {
            throw new ConflictException(Collections.singletonMap("skill name", skillDTO.getName()));
        } else {
            Skill skill = skillMapper.toEntity(skillDTO);
            skillRepository.save(skill);
        }
        return new MessageResponse(HttpServletResponse.SC_OK, "Tao skill thanh cong");
    }

    @Override
    public PaginationDTO filterSkill(String keyword, int pageNumber, int pageSize) {
        Page<Skill> page = skillRepository.filterSkill(keyword, PageRequest.of(pageNumber, pageSize));
        List<SkillDTO> list = new ArrayList<>();

        for (Skill skill: page.getContent()) {

            SkillDTO skillDTO = skillMapper.toDTO(skill);

            list.add(skillDTO);
        }
        return new PaginationDTO(list, page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());

    }

    @Override
    public MessageResponse updateSkill(SkillDTO skillDTO) {
        Skill skill = skillRepository.findById(skillDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("skill id", skillDTO.getId()))
        );
        if (skillRepository.findByName(skillDTO.getName()).isPresent()) {
            throw new ConflictException(Collections.singletonMap("skill name", skillDTO.getName()));
        }
        skill.setName(skillDTO.getName());
        skillRepository.save(skill);

        return new MessageResponse(HttpServletResponse.SC_OK, "update skill thanh cong");
    }

    @Override
    public MessageResponse deleteSkill(Long skillId) {
        Skill skill = skillRepository.findById(skillId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("Skill id khong ton tai", skillId))
        );
        List<Part> parts = partRepository.findBySkill_Id(skillId);
        if (!parts.isEmpty()) {
            throw new ConflictException(Collections.singletonMap("Da ton tai Part cua Skill nay", skillId));
        }

        skillRepository.delete(skill);

        return new MessageResponse(HttpServletResponse.SC_OK, "xoa skill thanh cong");
    }
}
