package com.aira.backend.logic;

import com.aira.backend.contracts.LoginRequest;
import com.aira.backend.contracts.RegisterRequest;
import com.aira.backend.domain.model.Role;
import com.aira.backend.domain.model.User;
import com.aira.backend.data.UserRepository;
import com.aira.backend.infrastructure.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // A new user joins us! Let’s make sure they’re unique, set their details,
    // and welcome them into our cozy system
    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists.");
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .location(request.location())
                .phoneNumber(request.phoneNumber())
                .dailyQueryLimit(request.dailyQueryLimit())
                .lastLogin(LocalDateTime.now())
                .role(Role.USER)
                .active(true)
                .build();

        userRepository.save(user);
    }

    // Checking if our friend is who they say they are
    // If yes, we refresh their lastLogin and send them a shiny new token
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return jwtTokenProvider.generateToken(user);
    }
}