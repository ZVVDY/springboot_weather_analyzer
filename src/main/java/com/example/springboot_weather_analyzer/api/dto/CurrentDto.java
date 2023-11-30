package com.example.springboot_weather_analyzer.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("current")
public class CurrentDto {

    @JsonProperty("temp_c")
    private double tempC;

    @JsonProperty("wind_kph")
    private double windKph;

    @JsonProperty("pressure_mb")
    private double pressureMb;

    private int humidity;

    private int cloud;

    @JsonProperty("condition")
    private ConditionDto conditionDto;

}
