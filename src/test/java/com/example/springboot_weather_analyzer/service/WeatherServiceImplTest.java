package com.example.springboot_weather_analyzer.service;

import com.example.springboot_weather_analyzer.dto.LocationDto;
import com.example.springboot_weather_analyzer.dto.WeatherDto;
import com.example.springboot_weather_analyzer.entity.Location;
import com.example.springboot_weather_analyzer.entity.Weather;
import com.example.springboot_weather_analyzer.exception.NoWeatherDataException;
import com.example.springboot_weather_analyzer.exception.WeatherDataRetrievalException;
import com.example.springboot_weather_analyzer.repository.WeatherRepository;
import com.example.springboot_weather_analyzer.service.impl.WeatherServiceImpl;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WeatherServiceImplTest {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Ignore
    void testGetMostRecentWeather() {
        Location location = new Location(1L, "Minsk", "Minsk",
                "Belarus", LocalDateTime.now());
        Weather weather = new Weather();
        weather.setId(1L);
        weather.setWindSpeed(22000);
        weather.setConditions("Sunny");
        weather.setTemperature(-5);
        weather.setPressure(1010.0);
        weather.setTimestamp(LocalDateTime.now());
        weather.setHumidity(60.0);
        weather.setLocation(location);
        when(weatherRepository.findTopByOrderByTimestampDesc()).thenReturn(Optional.of(weather));
        WeatherDto result = weatherService.getMostRecentWeather();
        assertNotNull(result);
        assertEquals(-5, result.getTemperature());
        assertEquals(22000, result.getWindSpeed());
        assertEquals(1010.0, result.getPressure());
        assertEquals(60.0, result.getHumidity());
        assertEquals("Sunny", result.getConditions());
        assertEquals("Minsk", result.getLocationDto().getName());
    }

    @Ignore
    void testCalculateAverageTemperature() {
        //Weather weather1 = new Weather(1L, 20.0, 5.0, 1000.0, 50.0, "Cloudy", new Location());
        //Weather weather2 = new Weather(2L, 30.0, 10.0, 1020.0, 70.0, "Rainy", new Location());
        List<Weather> weatherList = new ArrayList<>();
        //weatherList.add(weather1);
        //weatherList.add(weather2);
        when(weatherRepository.findByTimestampBetween(any(), any())).thenReturn(weatherList);
        var result = weatherService.calculateAverageTemperature("2023-11-30", "2023-12-01");
        assertNotNull(result);
        assertTrue(result.containsKey("average_temperature"));
        assertEquals(25.0, result.get("average_temperature"));
    }

    @Test
    void testCalculateAverageTemperatureNoData() {
        when(weatherRepository.findByTimestampBetween(any(), any())).thenReturn(new ArrayList<>());
        assertThrows(NoWeatherDataException.class, () -> weatherService.calculateAverageTemperature("2023-11-30", "2023-12-01"));
    }
}
