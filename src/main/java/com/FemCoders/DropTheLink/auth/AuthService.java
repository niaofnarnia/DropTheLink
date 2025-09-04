package com.FemCoders.DropTheLink.auth;

import com.FemCoders.DropTheLink.auth.dtos.LoginRequest;
import com.FemCoders.DropTheLink.auth.dtos.LoginResponse;
import com.FemCoders.DropTheLink.auth.dtos.RegisterRequest;
import com.FemCoders.DropTheLink.auth.security.jwt.JwtService;
import com.FemCoders.DropTheLink.exceptions.UserAlreadyExistsException;
import com.FemCoders.DropTheLink.exceptions.UserNotFoundException;
import com.FemCoders.DropTheLink.user.Role;
import com.FemCoders.DropTheLink.user.User;
import com.FemCoders.DropTheLink.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {
        Optional<User> existingUserByUsername = userRepository.findByUsernameIgnoreCase(request.username());
        if(existingUserByUsername.isPresent()){
            throw new UserAlreadyExistsException("Username: " + request.username() + " is not available.");
        }

        Optional<User> existingUserByEmail = userRepository.findByEmail(request.email());
        if(existingUserByEmail.isPresent()){
            throw new RuntimeException("Email already in use: " + request.email());
        }

        User newUser = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        userRepository.save(newUser);
    }

    public LoginResponse login(LoginRequest request){
        Optional<User> userOptional = userRepository.findByUsernameIgnoreCase(request.username());
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found with username: " + request.username());
        }

        User user = userOptional.get();
        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new BadCredentialsException("Login details are incorrect");
        }

        String token = jwtService.generateToken((UserDetails) user);
        return new LoginResponse(token);
    }
}