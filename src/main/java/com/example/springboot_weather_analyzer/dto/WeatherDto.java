package com.example.springboot_weather_analyzer.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class WeatherDto {

    private String city;
    private LocalDateTime timestamp;
    private Double temperature;
    private Double windSpeed;
    private Double pressure;
    private Double humidity;
    private String conditions;
    private String location;
}
