package com.example.toeicwebsite.data.repository;

import com.example.toeicwebsite.data.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long>{

//    Test findById(Long testId);
}
