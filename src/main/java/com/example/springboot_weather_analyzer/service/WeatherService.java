package com.example.springboot_weather_analyzer.service;

import com.example.springboot_weather_analyzer.dto.WeatherDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface WeatherService {
    WeatherDto getMostRecentWeather();
    double calculateAverageTemperature(LocalDate fromDate, LocalDate toDate);
}
