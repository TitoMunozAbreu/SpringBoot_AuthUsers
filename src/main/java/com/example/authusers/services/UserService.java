package com.example.authusers.services;


import com.example.authusers.models.Role;
import com.example.authusers.models.UserResponse;
import com.example.authusers.models.UserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean save(UserRequest userRequest);
    List<UserResponse> findAllUsers();
    String findUserRoleByEmail(String email);
}
