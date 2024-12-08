package com.example.toeicwebsite.service.impl;

import com.example.toeicwebsite.data.dto.MessageResponse;
import com.example.toeicwebsite.data.entity.*;
import com.example.toeicwebsite.data.repository.QuestionRepository;
import com.example.toeicwebsite.data.repository.TestDetailRepository;
import com.example.toeicwebsite.data.repository.TestRepository;
import com.example.toeicwebsite.data.repository.TopicRepository;
import com.example.toeicwebsite.exception.ResourceNotFoundException;
import com.example.toeicwebsite.service.QuestionService;
import com.example.toeicwebsite.service.StructureService;
import com.example.toeicwebsite.service.TestDetailService;
import com.example.toeicwebsite.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TestDetailServiceImpl implements TestDetailService {
    @Autowired
    private TestDetailRepository testDetailRepository;
    @Autowired
    private QuestionService questionService;

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicService topicService;
    @Autowired
    private StructureService structureService;

    @Override
    public MessageResponse saveListTestDetail(Long structureId, Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "không tìm thấy đề thi"))
        );

        List<Long> topicsListId = topicService.getListTopicsIDByStructure(structureId);

        for(Long topicId : topicsListId){
            List<Question> questionList = questionRepository.findAllByTopicId(topicId);
            if (questionList.isEmpty()) {
                throw new ResourceNotFoundException(Collections.singletonMap("message", "không tìm thấy câu hỏi"));
            }
            for (Question question : questionList){
                TestDetail testDetail = new TestDetail();

                testDetail.setTest(test);
                testDetail.setQuestion(question);

                testDetailRepository.save(testDetail);
            }
        }
        return new MessageResponse(200, "Tạo chi tiết đề thi từ structure id thành công");
    }

    @Override
    public MessageResponse saveTestDetail(Long kindOfStructureId, Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "không tìm thấy đề thi"))
        );
        List<Long> structureId = structureService.getListStructureIDByKindStructure(kindOfStructureId);
        for (Long id : structureId){
            List<Long> topicsListId = topicService.getListTopicsIDByStructure(id);
            for(Long topicId : topicsListId){
                List<Question> questionList = questionRepository.findAllByTopicId(topicId);
                if (questionList.isEmpty()) {
                    throw new ResourceNotFoundException(Collections.singletonMap("message", "không tìm thấy câu hỏi"));
                }
                for (Question question : questionList){
                    TestDetail testDetail = new TestDetail();

                    testDetail.setTest(test);
                    testDetail.setQuestion(question);

                    testDetailRepository.save(testDetail);
                }
            }
        }
        return new MessageResponse(200, "Tạo chi tiết đề thi từ kind structure id thành công");
    }
}
