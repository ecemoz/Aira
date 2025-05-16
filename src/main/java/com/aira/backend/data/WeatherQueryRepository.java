package com.aira.backend.data;

import com.aira.backend.domain.model.WeatherQuery;
import com.aira.backend.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface WeatherQueryRepository extends JpaRepository<WeatherQuery, UUID> {

    /**
     * Perfect for showing a user their query history — kind of like a personal weather diary
     * This method also helps enforce role-based access (Users see their own, Admins see all).
     */
    List<WeatherQuery> findAllByUser(User user);
}