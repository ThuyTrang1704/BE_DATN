package com.example.toeicwebsite.service.impl;

import com.example.toeicwebsite.data.entity.Structure;
import com.example.toeicwebsite.data.entity.Test;
import com.example.toeicwebsite.data.entity.TestStuctureDetail;
import com.example.toeicwebsite.data.repository.StructureRepository;
import com.example.toeicwebsite.data.repository.TestRepository;
import com.example.toeicwebsite.data.repository.TestStructureDetailRepository;
import com.example.toeicwebsite.exception.ResourceNotFoundException;
import com.example.toeicwebsite.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestStructureDetailRepository testStructureDetailRepository;
    @Autowired
    private StructureRepository structureRepository;
    @Autowired
    private TestRepository testRepository;

    @Override
    public Long saveTest(Long kindStructureId) {
        List<Structure> structures = structureRepository.findAllByKinfOfStructureId(kindStructureId);
        if(structures.isEmpty()) {
            throw new ResourceNotFoundException(Collections.singletonMap("message", "Không tìm thấy cấu trúc đề thi"));
        }

        Test test = new Test();
        Date currentDateTime = new Date();
        test.setCreate_at(currentDateTime);
        testRepository.save(test);

        for (Structure structure : structures) {
            TestStuctureDetail testStuctureDetail = new TestStuctureDetail();

            testStuctureDetail.setStruture(structure);
            testStuctureDetail.setTest(test);

            testStructureDetailRepository.save(testStuctureDetail);
        }

        return test.getId();
    }
}
