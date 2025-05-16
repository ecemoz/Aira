package com.aira.backend.contracts;

import java.time.LocalDateTime;

/**
 * Your personalized weather snapshot, right when you asked
 * Includes city, temperature, humidity, and a quick sky description
 * Designed for clarity and easy display in the UI
 * Timestamp helps you know how fresh the data is
 */

public record WeatherQueryResponse(
        String city,
        double temperature,
        String weatherDescription,
        int humidity,
        LocalDateTime queryTime
) {}
