package com.insurance.insurance_app.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.insurance.insurance_app.DTO.LoginRequest;
import com.insurance.insurance_app.DTO.RegisterRequest;
import com.insurance.insurance_app.Jwt.JwtUtils;
import com.insurance.insurance_app.Model.Role;
import com.insurance.insurance_app.Model.User;
import com.insurance.insurance_app.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public String register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER) // Default role
                .build();
        userRepository.save(user);
        return "User registered successfully!";
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtils.generateToken(user.getEmail());
    }
}
