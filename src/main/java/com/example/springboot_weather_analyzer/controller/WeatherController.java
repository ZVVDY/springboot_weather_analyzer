package com.example.springboot_weather_analyzer.controller;

import com.example.springboot_weather_analyzer.dto.WeatherDto;
import com.example.springboot_weather_analyzer.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current")
    public ResponseEntity<WeatherDto> getCurrentWeather() {
        try {
            WeatherDto weatherDto = weatherService.getMostRecentWeather();
            return new ResponseEntity<>(weatherDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while getting current weather", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/average")
    public ResponseEntity<Map<String, Double>> getAverageTemperature(@RequestBody Map<String, String> requestParams) {
        try {
            String from = requestParams.get("from");
            String to = requestParams.get("to");
            Map<String, Double> response = weatherService.calculateAverageTemperature(from, to);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request parameters", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while calculating average temperature", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
