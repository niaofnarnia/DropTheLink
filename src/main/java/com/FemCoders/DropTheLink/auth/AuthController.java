package com.FemCoders.DropTheLink.auth;

import com.FemCoders.DropTheLink.auth.dtos.LoginRequest;
import com.FemCoders.DropTheLink.auth.dtos.LoginResponse;
import com.FemCoders.DropTheLink.auth.dtos.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request){
        authService.register(request);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest request){
        LoginResponse response = authService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}