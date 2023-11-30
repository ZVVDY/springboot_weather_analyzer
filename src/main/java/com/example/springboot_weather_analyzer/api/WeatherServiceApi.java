package com.example.springboot_weather_analyzer.api;

import com.example.springboot_weather_analyzer.api.dto.ConditionDto;
import com.example.springboot_weather_analyzer.api.dto.CurrentDto;
import com.example.springboot_weather_analyzer.api.dto.LocationDto;
import com.example.springboot_weather_analyzer.api.dto.WeatherDataDto;
import com.example.springboot_weather_analyzer.entity.Location;
import com.example.springboot_weather_analyzer.entity.Weather;
import com.example.springboot_weather_analyzer.repository.WeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;


@Slf4j
@Service
public class WeatherServiceApi {
    private final String apiUrl = "https://weatherapi-com.p.rapidapi.com/forecast.json";
    private final String apiKey = "d6a9f167c3msh1f62a36dc6ab967p1464d7jsnff8b43aec92b";
    private WeatherRepository weatherRepository;
    private WebClient webClient;

    public WeatherServiceApi(WebClient webClient, WeatherRepository weatherRepository) {
        this.webClient = webClient;
        this.weatherRepository = weatherRepository;
    }

    public void retrieveDataFromApi(String city) throws JsonProcessingException {
        String apiUrlWithParams = apiUrl + "?q=" + city;

        String responseBody = webClient.get()
                .uri(apiUrlWithParams)
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ObjectMapper objectMapper = new ObjectMapper();
        WeatherDataDto weatherDataDto = objectMapper.readValue(responseBody, WeatherDataDto.class);
        dataConversionAndSummarization(weatherDataDto);
    }


    public void dataConversionAndSummarization(WeatherDataDto weatherDataDto) {
        ConditionDto conditionDto = weatherDataDto.getCurrent().getConditionDto();
        LocationDto locationDto = weatherDataDto.getLocation();
        CurrentDto currentDto = weatherDataDto.getCurrent();
        Location location = new Location();
        location.setCountry(locationDto.getCountry());
        location.setName(locationDto.getName());
        location.setRegion(locationDto.getRegion());
        location.setLocalDateTime(locationDto.getLocaltime());

        Weather weather = new Weather();
        weather.setConditions(conditionDto.getText());
        weather.setHumidity(currentDto.getHumidity());
        weather.setPressure(currentDto.getPressureMb());
        weather.setTemperature(currentDto.getTempC());
        weather.setWindSpeed(currentDto.getWindKph() * 1000);
        weather.setTimestamp(LocalDateTime.now());
        weather.setLocation(location);
        weatherRepository.save(weather);
        System.out.println(" Send to DB: " + weather);
    }
}

