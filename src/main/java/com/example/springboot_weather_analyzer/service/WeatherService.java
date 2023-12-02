package com.example.springboot_weather_analyzer.service;

import com.example.springboot_weather_analyzer.dto.WeatherDto;

import java.util.Map;

public interface WeatherService {
    WeatherDto getMostRecentWeather();

    Map<String, Double> calculateAverageTemperature(String from, String to);
}
