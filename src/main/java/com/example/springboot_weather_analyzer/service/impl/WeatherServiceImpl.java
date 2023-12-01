package com.example.springboot_weather_analyzer.service.impl;

import com.example.springboot_weather_analyzer.dto.WeatherDto;
import com.example.springboot_weather_analyzer.entity.Location;
import com.example.springboot_weather_analyzer.entity.Weather;
import com.example.springboot_weather_analyzer.repository.WeatherRepository;
import com.example.springboot_weather_analyzer.service.WeatherService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class WeatherServiceImpl implements WeatherService {

    private WeatherRepository weatherRepository;

    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public WeatherDto getMostRecentWeather() {
        Optional<Weather> mostRecentWeather = weatherRepository.findTopByOrderByTimestampDesc();

        if (mostRecentWeather.isPresent()) {
            Weather weather = mostRecentWeather.get();
            Location location = weather.getLocation();

            return new WeatherDto(
                    weather.getTemperature(),
                    weather.getWindSpeed(),
                    weather.getPressure(),
                    weather.getHumidity(),
                    weather.getConditions(),
                    location

            );
        }
        throw new NoSuchElementException("No weather data available");
    }

    public Map<String, Double> calculateAverageTemperature(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime fromDate = LocalDate.parse(from, formatter).atStartOfDay();
        LocalDateTime toDate = LocalDate.parse(to, formatter).atTime(LocalTime.MAX);
        List<Weather> weatherList = weatherRepository.findByTimestampBetween(fromDate, toDate);
        Map<String, Double> response = new HashMap<>();
        if (weatherList.isEmpty()) {
            //throw new NoWeatherDataException("No weather data available for the specified period");
        }

        double sumTemperature = 0.0;
        for (Weather weather : weatherList) {
            sumTemperature += weather.getTemperature();
        }
        double averageTemperature = sumTemperature / weatherList.size();
        response.put("average_temperature", averageTemperature);
        return response;
    }
}
