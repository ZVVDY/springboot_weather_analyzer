package com.example.springboot_weather_analyzer.repository;

import com.example.springboot_weather_analyzer.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findTopByOrderByTimestampDesc();

    List<Weather> findByTimestampBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
