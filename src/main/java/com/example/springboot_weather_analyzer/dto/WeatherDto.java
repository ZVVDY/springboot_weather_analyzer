package com.example.springboot_weather_analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class WeatherDto {

    private double temperature;
    private double windSpeed;
    private double pressure;
    private double humidity;
    private String conditions;
    private LocationDto locationDto;
}


