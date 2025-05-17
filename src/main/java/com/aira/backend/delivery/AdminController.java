package com.aira.backend.delivery;

import com.aira.backend.data.WeatherQueryRepository;
import com.aira.backend.domain.model.WeatherQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final WeatherQueryRepository queryRepository;

    @GetMapping("/queries")
    public ResponseEntity<List<WeatherQuery>> getAllQueries() {
        return ResponseEntity.ok(queryRepository.findAll());
    }
}
