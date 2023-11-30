package com.example.springboot_weather_analyzer.service.impl;

import com.example.springboot_weather_analyzer.dto.WeatherDto;
import com.example.springboot_weather_analyzer.entity.Location;
import com.example.springboot_weather_analyzer.entity.Weather;
import com.example.springboot_weather_analyzer.repository.WeatherRepository;
import com.example.springboot_weather_analyzer.service.WeatherService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public double calculateAverageTemperature(LocalDate fromDate, LocalDate toDate) {
        List<Weather> weatherList = weatherRepository.findByTimestampBetween(fromDate, toDate);

        if (weatherList.isEmpty()) {
            //throw new NoWeatherDataException("No weather data available for the specified period");
        }

        double sumTemperature = 0.0;
        for (Weather weather : weatherList) {
            sumTemperature += weather.getTemperature();
        }
        return sumTemperature / weatherList.size();
    }
}
