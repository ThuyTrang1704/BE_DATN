package com.example.toeicwebsite.service;

import com.example.toeicwebsite.data.dto.MessageResponse;
import com.example.toeicwebsite.data.dto.PaginationDTO;
import com.example.toeicwebsite.data.dto.TopicDTO;

import java.util.List;


public interface TopicService {
    List<TopicDTO> getTopicsByStructure(Long structureId);

    List<Long> getListTopicsIDByStructure(Long structureId);

    PaginationDTO filterTopic(String keyword, int pageNumber, int pageSize);

    MessageResponse createTopic(TopicDTO topicDTO);

    MessageResponse deleteTopic(Long topicId);

    MessageResponse updateTopic(TopicDTO topicDTO);
}
