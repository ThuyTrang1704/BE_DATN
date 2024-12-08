package com.example.toeicwebsite.data.repository;

import com.example.toeicwebsite.data.entity.Level;
import com.example.toeicwebsite.data.entity.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PartRepository extends JpaRepository<Part,Long> {
    Optional<Part> findByName(String name);

    List<Part> findBySkill_Id(Long id);
    @Query("select ncc from Part ncc " +

            "where " +
            "(:keyword = '' " +
            "OR  ncc.name LIKE %:keyword%)" +
            "ORDER BY ncc.id DESC")
    Page<Part> filterPart(@Param("keyword") String keyword, Pageable pageable);
}
