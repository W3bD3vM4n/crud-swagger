package com.example.crud_swagger.service;

import com.example.crud_swagger.model.User;
import com.example.crud_swagger.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepository iUserRepository;

    public List<User> getAllUsers() {
        return iUserRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return iUserRepository.findById(id);
    }

    public User saveUser(User user) {
        if (user.getId() != null && !iUserRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User with ID " + user.getId() + " does not exist.");
        }
        return iUserRepository.save(user);
    }

    public void deleteUser(Long id) {
        iUserRepository.deleteById(id);
    }

}
