package com.example.demo.service;

import com.example.demo.dto.dtoUser;
import com.example.demo.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<dtoUser> getUserByPage(Long id, Integer status, Pageable pageable);
    List<dtoUser> getUserByList();
    Users save(Users users);
    Users delete(Long id);
    Optional<Users> findById(Long id);
}
