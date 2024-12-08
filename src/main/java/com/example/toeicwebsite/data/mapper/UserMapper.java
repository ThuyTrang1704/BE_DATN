package com.example.toeicwebsite.data.mapper;

import com.example.toeicwebsite.data.dto.UserDTO;
import com.example.toeicwebsite.data.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);

    UserDTO toDTO(User user);

}
