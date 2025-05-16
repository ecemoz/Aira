package com.aira.backend.logic;

import com.aira.backend.contracts.WeatherQueryRequest;
import com.aira.backend.contracts.WeatherQueryResponse;
import com.aira.backend.data.WeatherQueryRepository;
import com.aira.backend.domain.model.User;
import com.aira.backend.domain.model.WeatherQuery;
import com.aira.backend.domain.model.QueryStatus;
import com.aira.backend.shared.WeatherApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherApiClient weatherApiClient;
    private final WeatherQueryRepository queryRepository;

    /**
     * Fetches weather data for a given city and user request
     * Logs the query, saves it to the database, and returns a friendly weather summary.
     * Redis caching is on the way to make things even faster!
     */
    public WeatherQueryResponse getWeather(WeatherQueryRequest request, User user) {
        var result = weatherApiClient.fetchWeather(request.city());

        WeatherQuery query = WeatherQuery.builder()
                .city(request.city())
                .queryTime(LocalDateTime.now())
                .responseJson(result.rawJson())
                .temperature(result.temperature())
                .weatherDescription(result.description())
                .humidity(result.humidity())
                .source(request.source())
                .status(QueryStatus.SUCCESS)
                .user(user)
                .build();

        queryRepository.save(query);

        return new WeatherQueryResponse(
                request.city(),
                result.temperature(),
                result.description(),
                result.humidity(),
                query.getQueryTime()
        );
    }
}