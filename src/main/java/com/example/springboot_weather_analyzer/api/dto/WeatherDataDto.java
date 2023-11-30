package com.example.springboot_weather_analyzer.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataDto {
    @JsonProperty("location")
    private LocationDto location;

    @JsonProperty("current")
    private CurrentDto current;
}
