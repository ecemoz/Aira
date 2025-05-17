package com.aira.backend.delivery;

import com.aira.backend.contracts.LoginRequest;
import com.aira.backend.contracts.RegisterRequest;
import com.aira.backend.logic.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles user registration and login requests
 * Delegates logic to AuthService and returns simple success messages or JWT tokens.
 */

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Kayıt başarılı! ☁️");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }
}
