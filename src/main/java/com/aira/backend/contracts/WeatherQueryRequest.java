package com.aira.backend.contracts;

import jakarta.validation.constraints.NotBlank;

/**
 * This request carries a tiny curiosity from the user — "What's the weather like over there?"
 * It includes the city to search for, and optionally, where the query came from (like WEB or MOBILE)
 */
public record WeatherQueryRequest(
        @NotBlank(message = "City name must be provided")
        String city,

        String source
) {}