package com.example.toeicwebsite.data.repository;

import com.example.toeicwebsite.data.entity.Skill;
import com.example.toeicwebsite.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

    @Query("select count(u) from User u where u.role.id != 1")
    long countUsersExcludingAdmin();
    User findByResetToken(String token);

    @Query("select ncc from User ncc " +

            "where " +
            "(:keyword = '' " +
            "OR  ncc.name LIKE %:keyword% " +
            "OR  ncc.email LIKE %:keyword% " +
            "OR  ncc.address LIKE %:keyword% " +
            "OR  ncc.phoneNumber LIKE %:keyword%) " +

            "AND ncc.role.id != 1" +
            "ORDER BY ncc.id DESC")
    Page<User> filterUser(@Param("keyword") String keyword, Pageable pageable);

//    void delete(Optional<User> resetToken);
}
