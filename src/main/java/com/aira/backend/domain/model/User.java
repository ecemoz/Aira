package com.aira.backend.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    //Every user should have a unique and unpredictable ID- UUID fits perfectly :)
    private UUID id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @Column(nullable = false)
    // This helps us deactivate users without actually deleting them — for soft-deletion support.
    private boolean active =  true;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    // Limiting the number of daily queries helps control usage and improves cache efficiency.
    private int dailyQueryLimit;

    @Column(nullable = false)
    // Knowing the last login time is useful for understanding user habits and retention.
    private LocalDateTime lastLogin;

    @Column(nullable = false)
    // Optional contact info — can be helpful for advanced verification or alerts.
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WeatherQuery> weatherQueries = new ArrayList<>();
}