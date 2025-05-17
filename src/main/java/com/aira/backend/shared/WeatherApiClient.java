package com.aira.backend.shared;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WeatherApiClient {

    @Value("${openweather.api.key}")
    private String apiKey;

    // Simple HTTP client for external API communication
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Fetches current weather data for the given city using OpenWeather API.
     * Results are cached based on city name to improve performance and reduce API calls.
     */
    @Cacheable(value = "weather", key = "#city", unless = "#result == null")
    public RawWeatherResult fetchWeather(String city) {
        String url = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s",
                city, apiKey
        );

        var response = restTemplate.getForObject(url, Map.class);

        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No weather data found.");
        }

        double temp = 0.0;
        int humidity = 0;
        String description = "unknown";

        Object mainObj = response.get("main");
        if (mainObj instanceof Map<?, ?> mainMap) {
            Object tempObj = mainMap.get("temp");
            if (tempObj instanceof Number t) {
                temp = t.doubleValue();
            }

            Object humidityObj = mainMap.get("humidity");
            if (humidityObj instanceof Number h) {
                humidity = h.intValue();
            }
        }

        Object weatherObj = response.get("weather");
        if (weatherObj instanceof List<?> weatherList && !weatherList.isEmpty()) {
            Object firstWeather = weatherList.get(0);
            if (firstWeather instanceof Map<?, ?> weatherMap) {
                Object descObj = weatherMap.get("description");
                if (descObj instanceof String d) {
                    description = d;
                }
            }
        }

        return new RawWeatherResult(temp, description, humidity, response.toString());
    }

    /**
     * Represents simplified weather data returned from OpenWeather API.
     */
    public record RawWeatherResult(double temperature, String description, int humidity, String rawJson) {}
}
