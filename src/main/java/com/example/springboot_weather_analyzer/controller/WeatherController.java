package com.example.springboot_weather_analyzer.controller;

import com.example.springboot_weather_analyzer.dto.WeatherDto;
import com.example.springboot_weather_analyzer.entity.Weather;
import com.example.springboot_weather_analyzer.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current")
    public ResponseEntity<WeatherDto> getCurrentWeather() {
        WeatherDto weatherDto = weatherService.getMostRecentWeather();
        return new ResponseEntity<>(weatherDto, HttpStatus.OK);
    }

    @GetMapping("/average")
    public ResponseEntity<Map<String, Double>> getAverageTemperature(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate from,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate to) {

        double averageTemperature = weatherService.calculateAverageTemperature(from, to);
        Map<String, Double> response = new HashMap<>();
        response.put("averageTemperature", averageTemperature);
        return ResponseEntity.ok(response);
    }
}
