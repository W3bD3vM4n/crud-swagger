package com.example.crud_swagger.mapper;

import com.example.crud_swagger.dto.UserCreateDTO;
import com.example.crud_swagger.dto.UserResponseDTO;
import com.example.crud_swagger.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    UserResponseDTO toResponseDTO(User user);

    List<UserResponseDTO> toResponseDTOList(List<User> users);

    User toEntity(UserCreateDTO dto);

}
