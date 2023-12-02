package com.example.springboot_weather_analyzer.exception;

public class WeatherDataRetrievalException extends RuntimeException {
    public WeatherDataRetrievalException(String message) {
        super(message);
    }

    public WeatherDataRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}
