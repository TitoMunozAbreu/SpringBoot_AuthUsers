package com.example.authusers.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest{
        @NotBlank
        private String name;

        @NotBlank(message = "Must include an email")
        @Email
        @Pattern(regexp = ".*@mail\\.com", message = "Must include a valid domain = example@mail.com")
        @Column(unique = true)
        private String email;

        @NotBlank(message = "Must inclue a password")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()-+]).{8}$", message = "At least 8 characters in length, Include at least one uppercase letter. Include at least one symbol (!@#$%^&*()_+). ")
        private String password;

        private List<Role> roles;
}
