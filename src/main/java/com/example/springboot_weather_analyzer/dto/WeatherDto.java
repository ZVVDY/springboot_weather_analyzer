package com.example.springboot_weather_analyzer.dto;

import com.example.springboot_weather_analyzer.entity.Location;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class WeatherDto {

    private double temperature;
    private double windSpeed;
    private double pressure;
    private double humidity;
    private String conditions;
}
