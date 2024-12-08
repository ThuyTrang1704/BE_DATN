package com.example.toeicwebsite.data.repository;

import com.example.toeicwebsite.data.entity.Level;
import com.example.toeicwebsite.data.entity.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level,Long> {
    Optional<Level> findByName(String name);

    @Query("select ncc from Level ncc " +

            "where " +
            "(:keyword = '' " +
            "OR  ncc.name LIKE %:keyword%)" +
            "ORDER BY ncc.id DESC")
    Page<Level> filterLevel(@Param("keyword") String keyword, Pageable pageable);
}
