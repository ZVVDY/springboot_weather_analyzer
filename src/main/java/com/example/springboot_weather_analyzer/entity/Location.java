package com.example.springboot_weather_analyzer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String region;
    private String country;
    @Column(name = "local_date_time")
    private LocalDateTime localDateTime;

    public Location(String minsk, String minsk1, String belarus, LocalDateTime date) {

    }
}
