package com.aira.backend.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "weather_queries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // The city that the user searched for — could be expanded to coordinates later
    private String city;

    // Full raw JSON from OpenWeather — great for debugging and future features
    @Column(columnDefinition = "TEXT")
    private String responseJson;

    private LocalDateTime queryTime;

    // Optional fields for analysis and performance insights
    private Double temperature;
    private String weatherDescription;
    private Integer humidity;
    private Long durationMs;

    // Where the query originated — Web UI, mobile, cron job, etc.
    private String source;

    // Status of the query (e.g., SUCCESS, ERROR)
    @Enumerated(EnumType.STRING)
    private QueryStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
