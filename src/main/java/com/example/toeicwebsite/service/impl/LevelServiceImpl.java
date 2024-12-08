package com.example.toeicwebsite.service.impl;

import com.example.toeicwebsite.data.dto.LevelDTO;
import com.example.toeicwebsite.data.dto.MessageResponse;
import com.example.toeicwebsite.data.dto.PaginationDTO;
import com.example.toeicwebsite.data.entity.Level;
import com.example.toeicwebsite.data.entity.Part;
import com.example.toeicwebsite.data.entity.Skill;
import com.example.toeicwebsite.data.entity.Topic;
import com.example.toeicwebsite.data.mapper.LevelMapper;
import com.example.toeicwebsite.data.repository.LevelRepository;
import com.example.toeicwebsite.data.repository.TopicRepository;
import com.example.toeicwebsite.exception.ConflictException;
import com.example.toeicwebsite.exception.ResourceNotFoundException;
import com.example.toeicwebsite.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class LevelServiceImpl implements LevelService {
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private LevelMapper levelMapper;

    @Autowired
    private TopicRepository topicRepository;
    @Override
    public MessageResponse createLevel(LevelDTO levelDTO) {
        if (levelRepository.findByName(levelDTO.getName()).isPresent()) {
            throw new ConflictException(Collections.singletonMap("skill name", levelDTO.getName()));
        } else {
            Level level = levelMapper.toEntity(levelDTO);
            levelRepository.save(level);
        }
        return new MessageResponse(HttpServletResponse.SC_OK, "Tao skill thanh cong");
    }

    @Override
    public PaginationDTO filterLevel(String keyword, int pageNumber, int pageSize) {
        Page<Level> page = levelRepository.filterLevel(keyword, PageRequest.of(pageNumber, pageSize));
        List<LevelDTO> list = new ArrayList<>();

        for (Level level: page.getContent()) {

            LevelDTO levelDTO = levelMapper.toDTO(level);

            list.add(levelDTO);
        }
        return new PaginationDTO(list, page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());

    }

    @Override
    public MessageResponse updateLevel(LevelDTO levelDTO) {
        Level level = levelRepository.findById(levelDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("level id", levelDTO.getId()))
        );
        if (levelRepository.findByName(levelDTO.getName()).isPresent()) {
            throw new ConflictException(Collections.singletonMap("level name", levelDTO.getName()));
        }
        level.setName(levelDTO.getName());
        levelRepository.save(level);

        return new MessageResponse(HttpServletResponse.SC_OK, "update level thanh cong");
    }

    @Override
    public MessageResponse deleteLevel(Long levelId) {
        Level level = levelRepository.findById(levelId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("Level id khong ton tai", levelId))
        );
        List<Topic> topics = topicRepository.findByLevel_Id(levelId);
        if (!topics.isEmpty()) {
            throw new ConflictException(Collections.singletonMap("Da ton tai topic cua level nay", levelId));
        }

        levelRepository.delete(level);

        return new MessageResponse(HttpServletResponse.SC_OK, "xoa Level thanh cong");
    }
}
