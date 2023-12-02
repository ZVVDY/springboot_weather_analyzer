package com.example.springboot_weather_analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class LocationDto {

    private String name;
    private String region;
    private String country;
    private LocalDateTime localDateTime;
}
