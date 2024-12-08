package com.example.toeicwebsite.service.impl;

import com.example.toeicwebsite.data.dto.*;
import com.example.toeicwebsite.data.entity.Role;
import com.example.toeicwebsite.data.entity.Skill;
import com.example.toeicwebsite.data.entity.User;
import com.example.toeicwebsite.data.mapper.UserMapper;
import com.example.toeicwebsite.data.repository.RoleRepository;
import com.example.toeicwebsite.data.repository.UserRepository;
import com.example.toeicwebsite.enumeration.ERole;
import com.example.toeicwebsite.exception.AccessDeniedException;
import com.example.toeicwebsite.exception.ConflictException;
import com.example.toeicwebsite.exception.ExceptionCustom;
import com.example.toeicwebsite.exception.ResourceNotFoundException;
import com.example.toeicwebsite.service.JwtService;
import com.example.toeicwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    private UserMapper userMapper;
    @Override
    public JwtResponseDTO loginUser(LoginDTO loginDTO) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());

        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("email: ", loginDTO.getEmail()))
        );

        if (user.isDeleted()) {
            throw new AccessDeniedException(Collections.singletonMap("message", "user is deleted"));
        }

        if (!checkValidPassword(loginDTO.getPassword(), user.getPassword()))
            throw new AccessDeniedException(Collections.singletonMap("message", "password is wrong"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                        loginDTO.getPassword()));



        Role role = user.getRole();

        String jwt = jwtService.generateToken(userDetails);

        return new JwtResponseDTO(jwt, loginDTO.getEmail(), role.getName());
    }

    @Override
    public MessageResponse createRegister(RegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent())
            throw new ConflictException(Collections.singletonMap("email", registerDTO.getEmail()));

        if (!registerDTO.getPassword().equals(registerDTO.getPasswordConfirm())) {
            throw new ExceptionCustom("Passwords do not match!");
        }

        if (registerDTO.getRoleId() == ERole.roleAdmin) {
            throw new ResourceNotFoundException(Collections.singletonMap("message", "admin access not allow"));
        } else {
            Role phanQuyen = roleRepository.findById(registerDTO.getRoleId()).orElseThrow(
                    () -> new ResourceNotFoundException(Collections.singletonMap("message", "role do not exist"))
            );

            User user = new User();

            user.setName(registerDTO.getName());
            user.setEmail(registerDTO.getEmail());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setRole(phanQuyen);

            userRepository.save(user);
        }
        return new MessageResponse(HttpServletResponse.SC_OK, "Tao user thanh cong");
    }

    @Override
    public MessageResponse updateNguoiDung(UserDTO userDTO) {
        User userCurrent = getNguoiDungByToken();


        User userUpdate = new User();

        userUpdate.setId(userCurrent.getId());
        userUpdate.setName(userDTO.getName());
        userUpdate.setAddress(userDTO.getAddress());
        userUpdate.setPhoneNumber(userDTO.getPhoneNumber());

        userUpdate.setEmail(userCurrent.getEmail());
        userUpdate.setPassword(userCurrent.getPassword());

        userUpdate.setRole(userCurrent.getRole());

        userRepository.save(userUpdate);
        return new MessageResponse(HttpServletResponse.SC_OK, "update user thanh cong");
    }

    @Override
    public UserDTO getNguoiDungHienTai() {
        User userCurrent = getNguoiDungByToken();
//        UserDTO userDTO = userMapper.toDTO(userCurrent);
        UserDTO userDTO = new UserDTO();

        userDTO.setId(userCurrent.getId());
        userDTO.setName(userCurrent.getName());
        userDTO.setEmail(userCurrent.getEmail());
        userDTO.setAddress(userCurrent.getAddress());
        userDTO.setPhoneNumber(userCurrent.getPhoneNumber());

        return userDTO;
    }

    @Override
    public PaginationDTO filterSkill(String keyword, int pageNumber, int pageSize) {
        Page<User> page = userRepository.filterUser(keyword, PageRequest.of(pageNumber, pageSize));
        List<UserDTO> list = new ArrayList<>();

        for (User user: page.getContent()) {

//            SkillDTO skillDTO = skillMapper.toDTO(skill);
            UserDTO userDTO = new UserDTO();

            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setAddress(user.getAddress());
            userDTO.setPhoneNumber(user.getPhoneNumber());
//            userDTO.setRole(user.getRole().getName());

            list.add(userDTO);
        }
        return new PaginationDTO(list, page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());

    }

    @Override
    public long countUsersExcludingAdmin() {
        return userRepository.countUsersExcludingAdmin();
    }


    private Boolean checkValidPassword(String password, String passwordEncoded) {

        return passwordEncoder.matches(password, passwordEncoded);
    }

    public User getNguoiDungByToken() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEmail = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "Tai khoan nay khong ton tai")));

        return userRepository.findById(userEmail.getId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "nguoi dung nay khong ton tai"))
        );
    }

}
