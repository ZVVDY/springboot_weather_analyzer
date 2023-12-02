package com.example.springboot_weather_analyzer.controller;

import com.example.springboot_weather_analyzer.dto.LocationDto;
import com.example.springboot_weather_analyzer.dto.WeatherDto;
import com.example.springboot_weather_analyzer.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void testGetCurrentWeather() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 11, 30, 0, 0);
        WeatherDto weatherDto = new WeatherDto(-5.0, 22000, 1004, 88, "Overcast",
                new LocationDto("Minsk", "Minsk", "Belarus", localDateTime));
        Mockito.when(weatherService.getMostRecentWeather()).thenReturn(weatherDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/current"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value(weatherDto.getTemperature()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.windSpeed").value(weatherDto.getWindSpeed()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pressure").value(weatherDto.getPressure()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.humidity").value(weatherDto.getHumidity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.conditions").value(weatherDto.getConditions()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.locationDto.name").value("Minsk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.locationDto.region").value("Minsk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.locationDto.country").value("Belarus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.locationDto.localDateTime").value("2023-11-30T00:00:00"));

        Mockito.verify(weatherService, Mockito.times(1)).getMostRecentWeather();
    }

    @Test
    public void testGetAverageTemperature() throws Exception {
        Map<String, Double> response = Collections.singletonMap("average_temperature", -5.0);
        Mockito.when(weatherService.calculateAverageTemperature(Mockito.anyString(), Mockito.anyString())).thenReturn(response);

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("from", "2023-01-01");
        requestParams.put("to", "2023-01-31");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/weather/average")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestParams)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.average_temperature").value(response.get("average_temperature")));

        Mockito.verify(weatherService, Mockito.times(1)).calculateAverageTemperature(Mockito.anyString(), Mockito.anyString());
    }
}