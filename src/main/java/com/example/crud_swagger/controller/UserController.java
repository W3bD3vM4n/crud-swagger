package com.example.crud_swagger.controller;

import com.example.crud_swagger.dto.UserCreateDTO;
import com.example.crud_swagger.dto.UserResponseDTO;
import com.example.crud_swagger.mapper.IUserMapper;
import com.example.crud_swagger.model.User;
import com.example.crud_swagger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private IUserMapper iUserMapper;

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return iUserMapper.mapToUserResponseDTOList(userService.getUsersListFromRepository());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserByIdFromRepository(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        if (userCreateDTO.getName() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        User user = iUserMapper.mapToUser(userCreateDTO);
        User savedUser = userService.saveUserFromRepository(user);
        UserResponseDTO responseDTO = iUserMapper.mapToUserResponseDTO(savedUser);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserCreateDTO userCreateDTO) {
        return userService.getUserByIdFromRepository(id)
            .map(existingUser -> {
                existingUser.setName(userCreateDTO.getName());
                existingUser.setEmail(userCreateDTO.getEmail());

                User updatedUser = userService.saveUserFromRepository(existingUser);
                UserResponseDTO responseDTO = iUserMapper.mapToUserResponseDTO(updatedUser);

                return ResponseEntity.ok(responseDTO);
            })
        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserFromRepository(id);
        return ResponseEntity.noContent().build();
    }

}
