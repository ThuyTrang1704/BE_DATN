package com.example.toeicwebsite.service.impl;

import com.example.toeicwebsite.data.dto.MessageResponse;
import com.example.toeicwebsite.data.dto.PaginationDTO;
import com.example.toeicwebsite.data.dto.SkillDTO;
import com.example.toeicwebsite.data.dto.TopicDTO;
import com.example.toeicwebsite.data.entity.*;
import com.example.toeicwebsite.data.repository.*;
import com.example.toeicwebsite.exception.ConflictException;
import com.example.toeicwebsite.exception.ResourceNotFoundException;
import com.example.toeicwebsite.service.QuestionService;
import com.example.toeicwebsite.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private StructureRepository structureRepository;
    @Autowired
    private QuestionService questionService;

    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<TopicDTO> getTopicsByStructure(Long structureId) {
        Structure structure = structureRepository.findById(structureId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "structure không tồn tại")));

        List<Topic> topics = topicRepository.findAllByLevelNameAndPartId
                (structure.getPart().getId(), structure.getLevel_of_topic());
        return topics.stream()
                .limit(structure.getNumber_of_topic())
                .map(this::topicConvertToDTO)
                .collect(Collectors.toList());
    }

    public List<Long> getListTopicsIDByStructure(Long structureId) {
        Structure structure = structureRepository.findById(structureId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "structure không tồn tại")));

        List<Topic> topics = topicRepository.findAllByLevelNameAndPartId
                (structure.getPart().getId(), structure.getLevel_of_topic());
        return topics.stream()
                .limit(structure.getNumber_of_topic())

                .map(Topic::getId)
                .collect(Collectors.toList());
    }

    @Override
    public PaginationDTO filterTopic(String keyword, int pageNumber, int pageSize) {
        Page<Topic> page = topicRepository.filterTopic(keyword, PageRequest.of(pageNumber, pageSize));
        List<TopicDTO> list = new ArrayList<>();

        for (Topic topic: page.getContent()) {
//            SkillDTO skillDTO = skillMapper.toDTO(skill);
            TopicDTO topicDTO = new TopicDTO();

            topicDTO.setId(topic.getId());
            topicDTO.setName(topic.getName());
            topicDTO.setContent(topic.getContent());
            topicDTO.setImageName(topic.getImage_name());
            topicDTO.setAudioName(topic.getAudio_name());
            topicDTO.setPathImage(topic.getImage_path());
            topicDTO.setPathAudio(topic.getAudio_path());
            topicDTO.setPartId(topic.getPart().getId());
            topicDTO.setLevelId(topic.getLevel().getId());


            list.add(topicDTO);
        }
        return new PaginationDTO(list, page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());

    }

    @Override
    public MessageResponse createTopic(TopicDTO topicDTO) {
        Level level = levelRepository.findById(topicDTO.getLevelId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "level không tồn tại")));
        Part part = partRepository.findById(topicDTO.getPartId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "part không tồn tại")));
//        if (topicRepository.find(partDTO.getName()).isPresent()) {
//            throw new ConflictException(Collections.singletonMap("part name", partDTO.getName()));
//        }
//        else {
//            Part part = partMapper.toEntity(partDTO);
            Topic topic = new Topic();
            topic.setName(topicDTO.getName());
            topic.setContent(topicDTO.getName());
            topic.setAudio_name(topicDTO.getAudioName());
            topic.setAudio_path(topicDTO.getAudioName());

            topic.setImage_name(topicDTO.getImageName());
            topic.setImage_path(topicDTO.getImageName());

            topic.setLevel(level);
            topic.setPart(part);

            topicRepository.save(topic);
//        }
        return new MessageResponse(HttpServletResponse.SC_OK, "tạo part thành công");
    }

    private TopicDTO topicConvertToDTO(Topic topic) {
        TopicDTO topicDTO = new TopicDTO();

        topicDTO.setId(topic.getId());
        topicDTO.setName(topic.getName());
        topicDTO.setContent(topic.getContent());
        topicDTO.setImageName(topic.getImage_name());
        topicDTO.setAudioName(topic.getAudio_name());
        topicDTO.setPathImage(topic.getImage_path());
        topicDTO.setPathAudio(topic.getAudio_path());
        topicDTO.setPartId(topic.getPart().getId());
        topicDTO.setLevelId(topic.getLevel().getId());
        topicDTO.setQuestions(questionService.getAllQuestionsWithAnswersByTopicId(topic.getId()));

        return topicDTO;
    }
    @Override
    public MessageResponse deleteTopic(Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("Topic khong ton tai",topicId))
        );
        List<Question> questions = questionRepository.findAllByTopicId(topicId);
        if (!questions.isEmpty()) {
            throw new ConflictException(Collections.singletonMap("Da ton tai topic cua part nay", topicId));
        }

        topicRepository.delete(topic);

        return new MessageResponse(HttpServletResponse.SC_OK, "xoa topic thanh cong");
    }

    @Override
    public MessageResponse updateTopic(TopicDTO topicDTO) {
        Topic topic = topicRepository.findById(topicDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("topic id nay khong ton tai", topicDTO.getId()))
        );

        Part part = partRepository.findById(topicDTO.getPartId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("part id nay khong ton dai", topicDTO.getPartId()))
        );
        Level level = levelRepository.findById(topicDTO.getLevelId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("level nay khong ton tai", topicDTO.getLevelId()))
        );

        topic.setName(topicDTO.getName());
        topic.setContent(topicDTO.getName());

        topic.setAudio_name(topicDTO.getAudioName());
        topic.setAudio_path(topicDTO.getAudioName());

        topic.setImage_name(topicDTO.getImageName());
        topic.setImage_path(topicDTO.getImageName());

        topic.setPart(part);
        topic.setLevel(level);

        topicRepository.save(topic);

        return new MessageResponse(HttpServletResponse.SC_OK, "update topic thanh cong");
    }
}
