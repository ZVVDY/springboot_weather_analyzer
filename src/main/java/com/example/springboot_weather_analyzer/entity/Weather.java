package com.example.springboot_weather_analyzer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double temperature;
    private double windSpeed;
    private double pressure;
    private double humidity;
    private String conditions;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
}
