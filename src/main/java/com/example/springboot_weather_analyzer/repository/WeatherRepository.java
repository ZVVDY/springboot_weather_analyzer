package com.example.springboot_weather_analyzer.repository;

import com.example.springboot_weather_analyzer.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
}
