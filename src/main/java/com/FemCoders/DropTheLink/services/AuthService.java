package com.FemCoders.DropTheLink.services;

import com.FemCoders.DropTheLink.dtos.user.LoginRequest;
import com.FemCoders.DropTheLink.dtos.user.LoginResponse;
import com.FemCoders.DropTheLink.dtos.user.RegisterRequest;
import com.FemCoders.DropTheLink.models.Role;
import com.FemCoders.DropTheLink.models.User;
import com.FemCoders.DropTheLink.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    public void register(RegisterRequest request) {
        User newUser = User.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .role(Role.USER)
                .build();
        userRepository.save(newUser);
    }
}