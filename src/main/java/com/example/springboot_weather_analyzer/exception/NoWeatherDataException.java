package com.example.springboot_weather_analyzer.exception;

public class NoWeatherDataException extends RuntimeException {
    public NoWeatherDataException(String message) {
        super(message);
    }
}
