package com.example.springboot_weather_analyzer.service;

import com.example.springboot_weather_analyzer.dto.WeatherDto;
import com.example.springboot_weather_analyzer.entity.Location;
import com.example.springboot_weather_analyzer.entity.Weather;
import com.example.springboot_weather_analyzer.repository.WeatherRepository;
import com.example.springboot_weather_analyzer.service.impl.WeatherServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceImplTest {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMostRecentWeather() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 11, 30, 0, 0);
        Location location = new Location();
        location.setName("Minsk");
        location.setRegion("Minsk");
        location.setCountry("Belarus");
        location.setLocalDateTime(dateTime);
        Weather weather = new Weather();
        weather.setTemperature(5.0);
        weather.setWindSpeed(22000.0);
        weather.setConditions("Clear");
        weather.setPressure(1010.0);
        weather.setLocation(location);
        weather.setHumidity(50.0);
        when(weatherRepository.findTopByOrderByTimestampDesc()).thenReturn(Optional.of(weather));
        WeatherDto weatherDto = weatherService.getMostRecentWeather();
        assertEquals(5.0, weatherDto.getTemperature());
        assertEquals(22000.0, weatherDto.getWindSpeed());
        assertEquals(1010.0, weatherDto.getPressure());
        assertEquals(50.0, weatherDto.getHumidity());
        assertEquals("Clear", weatherDto.getConditions());
        assertEquals(location, weatherDto.getLocation());
    }

    @Test
    public void testCalculateAverageTemperature() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 11, 30, 0, 0);
        Location location = new Location();
        location.setName("Minsk");
        location.setRegion("Minsk");
        location.setCountry("Belarus");
        location.setLocalDateTime(dateTime);
        Weather weather = new Weather();
        weather.setTemperature(-5.0);
        weather.setWindSpeed(22000.0);
        weather.setConditions("Clear");
        weather.setPressure(1010.0);
        weather.setLocation(location);
        weather.setHumidity(50.0);
        Weather weather2 = new Weather();
        weather2.setTemperature(-7.0);
        weather2.setWindSpeed(22000.0);
        weather2.setConditions("Clear");
        weather2.setPressure(1010.0);
        weather2.setLocation(location);
        weather2.setHumidity(50.0);
        LocalDateTime fromDateTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime toDateTime = LocalDateTime.of(2023, 1, 31, 23, 59);
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(weather);
        weatherList.add(weather2);
        double sumTemperature = weatherList.stream().mapToDouble(Weather::getTemperature).sum();
        double averageTemperature = sumTemperature / weatherList.size();
        assertEquals(-6, averageTemperature);
    }
}