package com.example.toeicwebsite.data.repository;

import com.example.toeicwebsite.data.entity.Result;
import com.example.toeicwebsite.data.entity.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultRepository extends JpaRepository<Result, Long> {
    @Query("select ncc from Result ncc " +

            "where " +
            "ncc.user.id = :userId AND"+
            "(:keyword = '' " +
            "OR  ncc.status LIKE %:keyword%)" +
            "ORDER BY ncc.id DESC")
    Page<Result> filterResult(@Param("keyword") String keyword,
                              @Param("userId") Long userId,
                              Pageable pageable);

    @Query("select count(u) from Result u")
    long countResult();
}
