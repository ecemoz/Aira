package com.aira.backend.delivery;

import com.aira.backend.contracts.WeatherQueryRequest;
import com.aira.backend.contracts.WeatherQueryResponse;
import com.aira.backend.data.UserRepository;
import com.aira.backend.logic.WeatherService;
import com.aira.backend.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<WeatherQueryResponse> getWeather(
            @RequestParam String city,
            @RequestParam(required = false, defaultValue = "API") String source,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var response = weatherService.getWeather(
                new WeatherQueryRequest(city, source),
                user
        );

        return ResponseEntity.ok(response);
    }
}
