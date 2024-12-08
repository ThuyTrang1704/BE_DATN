package com.example.toeicwebsite.data.repository;

import com.example.toeicwebsite.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Boolean existsByName(String name);
}
