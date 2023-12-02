package com.example.springboot_weather_analyzer.service;

import com.example.springboot_weather_analyzer.dto.WeatherDto;
import com.example.springboot_weather_analyzer.entity.Location;
import com.example.springboot_weather_analyzer.entity.Weather;
import com.example.springboot_weather_analyzer.exception.NoWeatherDataException;
import com.example.springboot_weather_analyzer.repository.WeatherRepository;
import com.example.springboot_weather_analyzer.service.impl.WeatherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
public class WeatherServiceImplTest {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Before
    public void setUp() {
        weatherRepository = mock(WeatherRepository.class);
        weatherService = new WeatherServiceImpl(weatherRepository);
    }

    @Ignore
    public void testGetMostRecentWeather() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 11, 30, 0, 0);
        Location location = new Location(1L, "Minsk", "Minsk", "Belarus", dateTime);
        Weather weather = new Weather(1L, -5.0, 22000.0, 1010.0, 50.0, "Clear", dateTime, location);
        when(weatherRepository.findTopByOrderByTimestampDesc()).thenReturn(Optional.empty());
        WeatherDto weatherDto = weatherService.getMostRecentWeather();
        assertNotNull(weatherDto);
        assertThrows(NoWeatherDataException.class, () -> weatherService.getMostRecentWeather());
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
    public void testCalculateAverageTemperature() {
    }
}
