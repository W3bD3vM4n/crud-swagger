package com.example.crud_swagger.controller;

import com.example.crud_swagger.dto.CreateUserDTO;
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
        return iUserMapper.toResponseDTOList(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {
        if (createUserDTO.getName() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        User user = iUserMapper.toEntity(createUserDTO);
        User savedUser = userService.saveUser(user);
        UserResponseDTO responseDTO = iUserMapper.toResponseDTO(savedUser);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody CreateUserDTO createUserDTO) {
        return userService.getUserById(id)
            .map(existingUser -> {
                existingUser.setName(createUserDTO.getName());
                existingUser.setEmail(createUserDTO.getEmail());

                User updatedUser = userService.saveUser(existingUser);
                UserResponseDTO responseDTO = iUserMapper.toResponseDTO(updatedUser);

                return ResponseEntity.ok(responseDTO);
            })
        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
