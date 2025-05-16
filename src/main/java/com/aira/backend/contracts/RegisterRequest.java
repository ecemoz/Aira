package com.aira.backend.contracts;

import jakarta.validation.constraints.*;

/**
 * This record handles the user's registration request
 * It's like a welcome form — we collect the essentials and make sure everything looks good.
 * Validation annotations ensure we guide the user with friendly messages if anything’s missing
 */
public record RegisterRequest(

        @NotBlank(message = "Email is required")
        @Email
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Location is required")
        String location,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @Min(value = 1, message = "Minimum daily query limit is 1")
        int dailyQueryLimit
) {}
