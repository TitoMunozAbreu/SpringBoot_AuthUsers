package com.example.authusers.models;

import java.util.List;

public record UserResponse(
        String name,
        String email,
        List<Role> roles
) {}
