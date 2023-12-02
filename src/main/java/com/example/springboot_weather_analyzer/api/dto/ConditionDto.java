package com.example.springboot_weather_analyzer.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("condition")
public class ConditionDto {

    @JsonProperty("text")
    private String text;
}