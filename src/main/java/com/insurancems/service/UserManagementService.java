package com.insurancems.service;

import com.insurancems.entity.User;
import java.util.List;

public interface UserManagementService {
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    List<User> getUsersByRole(String role);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 