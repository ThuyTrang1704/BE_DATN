package com.example.toeicwebsite.util;


import com.example.toeicwebsite.data.entity.Role;
import com.example.toeicwebsite.data.entity.User;
import com.example.toeicwebsite.data.repository.RoleRepository;
import com.example.toeicwebsite.data.repository.UserRepository;
import com.example.toeicwebsite.enumeration.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Init {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostConstruct
    void inIt() {
        inIt_Role();
        inIt_User();
    }

    private void inIt_Role() {
        for (ERole eRole : ERole.values()) {

            if (!roleRepository.existsByName(eRole.toString())) {
                Role role = new Role();
                role.setName(eRole.toString());

                roleRepository.save(role);
            }
        }
    }

    private void inIt_User() {
        String email = "admin@gmail.com";
        String password = "123456";
        String tenDangNhap = "admin123";
        if (!userRepository.existsByEmail(email)) {
            User user = new User();

            user.setName(tenDangNhap);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(roleRepository.findById(ERole.roleAdmin).orElse(null));

            userRepository.save(user);
        }
    }
}
