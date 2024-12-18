package com.example.crud_swagger.repository;

import com.example.crud_swagger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
}
