package com.example.toeicwebsite.data.repository;

import com.example.toeicwebsite.data.entity.TestDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestDetailRepository extends JpaRepository<TestDetail, Long>{
    @Override
    Optional<TestDetail> findById(Long testDetailId);

    List<TestDetail> findAllByQuestionId(Long questionId);
}
