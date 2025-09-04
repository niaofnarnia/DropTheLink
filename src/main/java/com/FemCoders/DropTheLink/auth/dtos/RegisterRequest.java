package com.FemCoders.DropTheLink.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest (
        @NotBlank(message = "Username cannot be blank")
        @Size(max = 20, message = "Username must not exceed 20 characters")
        String username,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Enter a valid email", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Pattern(message = "Password must contain a minimum of 8 characters, including a number, one uppercase letter, one lowercase letter and one special character", regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()+=.])(?=\\S+$).{8,}$")
        String password
) {}