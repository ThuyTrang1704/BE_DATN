package com.example.toeicwebsite.data.repository;

import com.example.toeicwebsite.data.entity.Skill;
import com.example.toeicwebsite.data.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long>{
    List<Topic> findByLevel_Id(Long levelId);

    List<Topic> findByPart_Id(Long partId);


    @Query("SELECT t FROM Topic t WHERE (t.part.id = :partId AND t.level.name = :levelOfTopic)" +
            "ORDER BY RAND()"
    )
    List<Topic> findAllByLevelNameAndPartId(@Param("partId") Long partId,
                                            @Param("levelOfTopic") String levelOfTopic);

    @Query("SELECT ncc FROM Topic ncc " +
            "WHERE (:keyword = '' " +
            "OR ncc.name LIKE %:keyword% " +
            "OR ncc.image_name LIKE %:keyword% " +
            "OR ncc.level.name LIKE %:keyword% " +
            "OR ncc.part.name LIKE %:keyword% " +

            "OR ncc.audio_name LIKE %:keyword%) " +
            "ORDER BY ncc.id DESC")
    Page<Topic> filterTopic(@Param("keyword") String keyword, Pageable pageable);

}
