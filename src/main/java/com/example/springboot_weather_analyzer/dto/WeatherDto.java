package com.example.springboot_weather_analyzer.dto;

import com.example.springboot_weather_analyzer.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


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


