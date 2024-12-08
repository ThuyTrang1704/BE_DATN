package com.example.toeicwebsite.service.impl;

import com.example.toeicwebsite.data.dto.*;
import com.example.toeicwebsite.data.entity.Part;
import com.example.toeicwebsite.data.entity.Skill;
import com.example.toeicwebsite.data.entity.Topic;
import com.example.toeicwebsite.data.mapper.PartMapper;
import com.example.toeicwebsite.data.repository.PartRepository;
import com.example.toeicwebsite.data.repository.SkillRepository;
import com.example.toeicwebsite.data.repository.TopicRepository;
import com.example.toeicwebsite.exception.ConflictException;
import com.example.toeicwebsite.exception.ResourceNotFoundException;
import com.example.toeicwebsite.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PartServiceImpl implements PartService {
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private PartMapper partMapper;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Override
    public MessageResponse createPart(PartDTO partDTO) {
        Skill skill = skillRepository.findById(partDTO.getSkillId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "kỹ năng không tồn tại")));
        if (partRepository.findByName(partDTO.getName()).isPresent()) {
            throw new ConflictException(Collections.singletonMap("part name", partDTO.getName()));
        }
        else {
//            Part part = partMapper.toEntity(partDTO);
            Part part = new Part();


            part.setName(partDTO.getName());
            part.setPart_number(partDTO.getPart_number());
            part.setDescription(partDTO.getDescription());
//            part.setTest(partDTO.getTest());
            part.setSkill(skill);

            partRepository.save(part);
        }
        return new MessageResponse(HttpServletResponse.SC_OK, "tạo part thành công");
    }

    @Override
    public PaginationDTO filterPart(String keyword, int pageNumber, int pageSize) {
        Page<Part> page = partRepository.filterPart(keyword, PageRequest.of(pageNumber, pageSize));
        List<PartShowDTO> list = new ArrayList<>();

        for (Part part: page.getContent()) {

            PartShowDTO partDTO = partMapper.toDTO(part);
            list.add(partDTO);
        }
        return new PaginationDTO(list, page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());

    }

    @Override
    public MessageResponse updatePart(PartDTO partDTO) {
        Part part = partRepository.findById(partDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("part id", partDTO.getId()))
        );
        Skill skill = skillRepository.findById(partDTO.getSkillId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "kỹ năng không ton tại"))
        );
        if (partRepository.findByName(partDTO.getName()).isPresent()) {
            throw new ConflictException(Collections.singletonMap("part name", partDTO.getName()));
        }
        part.setName(partDTO.getName());
        part.setDescription(partDTO.getDescription());
        part.setPart_number(partDTO.getPart_number());
        part.setSkill(skill);

        partRepository.save(part);

        return new MessageResponse(HttpServletResponse.SC_OK, "update part thanh cong");
    }

    @Override
    public MessageResponse deletePart(Long partId) {
        Part part = partRepository.findById(partId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("Part id khong ton tai", partId))
        );
        List<Topic> topics = topicRepository.findByPart_Id(partId);
        if (!topics.isEmpty()) {
            throw new ConflictException(Collections.singletonMap("Da ton tai topic cua part nay", partId));
        }

        partRepository.delete(part);

        return new MessageResponse(HttpServletResponse.SC_OK, "xoa part thanh cong");
    }
}
