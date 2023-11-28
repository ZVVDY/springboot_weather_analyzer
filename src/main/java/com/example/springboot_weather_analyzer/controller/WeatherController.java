package com.example.springboot_weather_analyzer.controller;

import com.example.springboot_weather_analyzer.entity.Weather;
import com.example.springboot_weather_analyzer.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Weather> getCurrentWeather() {

        return null;
    }

    @GetMapping("/average")
    public ResponseEntity<Map<String, Double>> getAverageTemperature(@RequestParam String from,
                                                                     @RequestParam String to) {

        return null;
    }
}
