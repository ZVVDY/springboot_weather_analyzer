package com.example.springboot_weather_analyzer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherScheduler {
    private final WeatherServiceApi weatherServiceApi;

    @Autowired
    public WeatherScheduler(WeatherServiceApi weatherServiceApi) {
        this.weatherServiceApi = weatherServiceApi;
    }

    @Scheduled(fixedRateString = "${weather.api.fetch.interval}")
    public void fetchWeatherData() {

        //Weather weather = weatherServiceApi.getWeatherData("City");

    }
}
