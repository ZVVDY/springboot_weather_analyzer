package com.example.springboot_weather_analyzer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private LocalDateTime timestamp;
    private Double temperature;
    private Double windSpeed;
    private Double pressure;
    private Double humidity;
    private String conditions;
    private String location;
}
