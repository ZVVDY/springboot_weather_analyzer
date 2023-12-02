package com.example.springboot_weather_analyzer.service;

import com.example.springboot_weather_analyzer.dto.WeatherDto;
import com.example.springboot_weather_analyzer.entity.Location;
import com.example.springboot_weather_analyzer.entity.Weather;
import com.example.springboot_weather_analyzer.repository.WeatherRepository;
import com.example.springboot_weather_analyzer.service.impl.WeatherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
public class WeatherServiceImplTest {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Test
    public void testGetMostRecentWeather() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 11, 30, 0, 0);
        Location location = new Location(1L, "Minsk", "Minsk", "Belarus", dateTime);
        Weather weather = new Weather(1L, -5.0, 22000.0, 1010.0, 50.0, "Clear", dateTime, location);
        when(weatherRepository.findTopByOrderByTimestampDesc()).thenReturn(Optional.of(weather));
        WeatherDto weatherDto = weatherService.getMostRecentWeather();
        assertNotNull(weatherDto);
        assertEquals(-5.0, weatherDto.getTemperature());
        assertEquals(22000.0, weatherDto.getWindSpeed());
        assertEquals(1010.0, weatherDto.getPressure());
        assertEquals(50.0, weatherDto.getHumidity());
        assertEquals("Clear", weatherDto.getConditions());
        assertNotNull(weatherDto.getLocationDto());
        assertEquals("Minsk", weatherDto.getLocationDto().getName());
        assertEquals("Minsk", weatherDto.getLocationDto().getRegion());
        assertEquals("Belarus", weatherDto.getLocationDto().getCountry());
        assertEquals(dateTime, weatherDto.getLocationDto().getLocalDateTime());
        verify(weatherRepository, times(1)).findTopByOrderByTimestampDesc();
    }

    @Test
    public void testGetMostRecentWeatherNoData() {
        when(weatherRepository.findTopByOrderByTimestampDesc()).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> weatherService.getMostRecentWeather());
        verify(weatherRepository, times(1)).findTopByOrderByTimestampDesc();
    }
}
//    @Test
//    public void testCalculateAverageTemperature() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDateTime dateTime = LocalDateTime.parse("2023-01-01", formatter);
//        LocalDateTime dateTime2 = LocalDateTime.parse("2023-11-30", formatter);
//
//        Location location = new Location(1L, "Minsk", "Minsk", "Belarus", dateTime2);
//        Weather weather1 = new Weather(1L, -5.0, 22000.0, 1010.0, 50.0, "Clear", dateTime, location);
//        Weather weather2 = new Weather(2L, -7.0, 22000.0, 1010.0, 50.0, "Clear", dateTime2, location);
//
//        when(weatherRepository.findByTimestampBetween(dateTime, dateTime2)).thenReturn(List.of(weather1, weather2));
//
//        Map<String, Double> result = weatherService.calculateAverageTemperature("2023-01-01", "2023-11-30");
//
//        assertNotNull(result);
//        assertTrue(result.containsKey("average_temperature"));
//        assertEquals(-6.0, result.get("average_temperature"), 0.01);
//
//        verify(weatherRepository, times(1)).findByTimestampBetween(dateTime, dateTime2);
//    }
//
//
//    @Test
//    public void testCalculateAverageTemperatureNoData() {
//        when(weatherRepository.findByTimestampBetween(any(), any())).thenReturn(Collections.emptyList());
//        Map<String, Double> result = weatherService.calculateAverageTemperature("2023-01-01", "2023-01-31");
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//        verify(weatherRepository, times(1)).findByTimestampBetween(any(), any());
//    }

