package com.FemCoders.DropTheLink.auth;

import com.FemCoders.DropTheLink.auth.dtos.LoginRequest;
import com.FemCoders.DropTheLink.auth.dtos.LoginResponse;
import com.FemCoders.DropTheLink.auth.dtos.RegisterRequest;
import com.FemCoders.DropTheLink.user.Role;
import com.FemCoders.DropTheLink.user.User;
import com.FemCoders.DropTheLink.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    public void register(RegisterRequest request) {
        Optional<User> existingUserByUsername = userRepository.findByUsernameIgnoreCase(request.username());
        if(existingUserByUsername.isPresent()){
            throw new RuntimeException("Username: " + request.username() + " is not available.");
        }
        Optional<User> existingUserByEmail = userRepository.findByEmail(request.email());
        if(existingUserByEmail.isPresent()){
            throw new RuntimeException("Email already in use: " + request.email());
        }
        User newUser = User.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .role(Role.USER)
                .build();
        userRepository.save(newUser);
    }
    public LoginResponse login(LoginRequest request){
        Optional<User> userOptional = userRepository.findByUsernameIgnoreCase(request.username());
        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found with username: " + request.username());
        }
        User user = userOptional.get();
        if(!request.password().equals(user.getPassword())){
            throw new RuntimeException("Login details are incorrect");
        }
        String token = "jwt token for: " + user.getUsername();
        return new LoginResponse(token);
    }
}