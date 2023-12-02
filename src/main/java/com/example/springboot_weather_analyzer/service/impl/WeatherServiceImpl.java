package com.example.springboot_weather_analyzer.service.impl;

import ch.qos.logback.classic.Logger;
import com.example.springboot_weather_analyzer.dto.LocationDto;
import com.example.springboot_weather_analyzer.dto.WeatherDto;
import com.example.springboot_weather_analyzer.entity.Location;
import com.example.springboot_weather_analyzer.entity.Weather;
import com.example.springboot_weather_analyzer.exception.NoWeatherDataException;
import com.example.springboot_weather_analyzer.exception.WeatherDataRetrievalException;
import com.example.springboot_weather_analyzer.repository.WeatherRepository;
import com.example.springboot_weather_analyzer.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(WeatherServiceImpl.class);
    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Logger getLogger() {
        return logger;
    }
    @Override
    public WeatherDto getMostRecentWeather() {
        try {
            Optional<Weather> mostRecentWeather = weatherRepository.findTopByOrderByTimestampDesc();

            if (mostRecentWeather.isPresent()) {
                Weather weather = mostRecentWeather.get();
                Location location = weather.getLocation();
                LocationDto locationDto = new LocationDto(
                        location.getName(),
                        location.getRegion(),
                        location.getCountry(),
                        location.getLocalDateTime()
                );
                return new WeatherDto(
                        weather.getTemperature(),
                        weather.getWindSpeed(),
                        weather.getPressure(),
                        weather.getHumidity(),
                        weather.getConditions(),
                        locationDto
                );
            } else {
                logger.warn("No weather data available");
                throw new NoWeatherDataException("No weather data available");
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the most recent weather data", e);
            throw new WeatherDataRetrievalException("Failed to retrieve most recent weather data", e);
        }
    }

    public Map<String, Double> calculateAverageTemperature(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime fromDate;
        LocalDateTime toDate;

        try {
            fromDate = LocalDate.parse(from, formatter).atStartOfDay();
            toDate = LocalDate.parse(to, formatter).atTime(LocalTime.of(23, 59, 59, 999999999));
        } catch (DateTimeParseException e) {
            logger.error("Error parsing date strings: from={}, to={}", from, to, e);
            throw new IllegalArgumentException("Invalid date format", e);
        }

        List<Weather> weatherList;
        try {
            weatherList = weatherRepository.findByTimestampBetween(fromDate, toDate);
        } catch (Exception e) {
            logger.error("Error retrieving weather data from the repository", e);
            throw new WeatherDataRetrievalException("Error retrieving weather data", e);
        }

        if (weatherList.isEmpty()) {
            logger.warn("No weather data available for the specified period: from={}, to={}", from, to);
            throw new NoWeatherDataException("No weather data available for the specified period");
        }

        double sumTemperature = 0.0;
        for (Weather weather : weatherList) {
            sumTemperature += weather.getTemperature();
        }

        double averageTemperature = sumTemperature / weatherList.size();

        Map<String, Double> response = new HashMap<>();
        response.put("average_temperature", averageTemperature);

        return response;
    }
}

