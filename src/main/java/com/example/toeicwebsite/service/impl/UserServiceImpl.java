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
import com.example.toeicwebsite.service.MailService;
import com.example.toeicwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    private MailService mailService;

    @Value("${reset.password.url}")
    private String resetPasswordUrl;


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

    @Override
    public void sendResetPasswordToken(String email) {
        // Tìm user theo email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        // Tạo token reset password (hết hạn sau 10 phút)
        String resetToken = jwtService.generateResetPasswordToken(email, 10);

        // Lưu token vào DB
        user.setResetToken(resetToken);
        userRepository.save(user);

        // Gửi email chứa token
        String resetLink = resetPasswordUrl + resetToken + "&email=" + email + "&newPassword=";


        // Chuẩn bị dữ liệu động cho template
        Map<String, Object> templateModel = Map.of(
                "userName", user.getName(),
                "resetLink", resetLink
        );
        // Gửi email với template
        mailService.sendEmailWithTemplate(email, "Reset Your Password", "forgot-password", templateModel);
    }

    @Override
    public boolean validateResetPasswordToken(String token, String email) {
        // Kiểm tra token hợp lệ
        return jwtService.isResetTokenValid(token, email);
    }

    @Override
    public void resetPassword(String token, String email, String newPassword) {
        // Validate token
        if (!validateResetPasswordToken(token, email)) {
            throw new IllegalArgumentException("Token is invalid or expired");
        }

        // Tìm user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        // Đặt lại mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // Xóa token sau khi đặt lại mật khẩu
        userRepository.save(user);
    }

    @Override
    public MessageResponse changePassword(String email, String oldPassword, String newPassword) {
        try {
            // Tìm user theo email
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("User not found."));

            // Kiểm tra mật khẩu cũ
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new IllegalArgumentException("Old password is incorrect.");
            }

            // Mã hóa mật khẩu mới và lưu vào database
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);

            // Trả về MessageResponse với httpCode 200 (OK) và thông báo thành công
            return new MessageResponse(200, "Password changed successfully.");

        } catch (IllegalArgumentException e) {
            // Trả về MessageResponse với httpCode 400 (Bad Request) và thông báo lỗi
            return new MessageResponse(400, e.getMessage());
        } catch (Exception e) {
            // Trả về MessageResponse với httpCode 500 (Internal Server Error) và thông báo lỗi
            return new MessageResponse(500, "Something went wrong!");
        }
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
