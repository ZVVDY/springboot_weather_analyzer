package com.example.springboot_weather_analyzer.controller;

import com.example.springboot_weather_analyzer.dto.WeatherDto;
import com.example.springboot_weather_analyzer.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/average")
    public ResponseEntity<Map<String, Double>> getAverageTemperature(@RequestBody Map<String, String> requestParams) {
        String from = requestParams.get("from");
        String to = requestParams.get("to");
        Map<String, Double> response = weatherService.calculateAverageTemperature(from, to);
        return ResponseEntity.ok(response);
    }
}
