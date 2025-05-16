package com.aira.backend.contracts;

import jakarta.validation.constraints.*;

/**
 * This record carries the tiniest details with the biggest trust: a user's login info
 * Just an email and a password — simple, right? But they’re enough to open the door to their space
 * We don’t just log them in — we greet them, quietly saying: “Welcome back, we've missed you.”
 */

public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank String password
) {}
