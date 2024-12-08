package com.example.toeicwebsite.data.repository;

import com.example.toeicwebsite.data.entity.Skill;
import com.example.toeicwebsite.data.entity.Structure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StructureRepository extends JpaRepository<Structure, Long>{
    List<Structure> findAllByKinfOfStructureId(Long kindStructureId);
    @Query("select count(u) from Structure u")
    long countStructureCreate();

    @Query("select ncc from Structure ncc " +

            "where " +
            "(:keyword = '' " +
            "OR  ncc.name LIKE %:keyword% " +
            "OR  ncc.level_of_topic LIKE %:keyword% " +
            "OR  ncc.name LIKE %:keyword%) " +

            "ORDER BY ncc.id DESC")
    Page<Skill> filterSkill(@Param("keyword") String keyword, Pageable pageable);
}
