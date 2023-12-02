package com.example.springboot_weather_analyzer.api.service;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;


@Slf4j
@Service
public class WeatherServiceApi {

    private final WeatherRepository weatherRepository;
    private final WebClient webClient;

    @Value("${weather.api.url}")
    private String API_URL;

    @Value("${weather.api.key}")
    private String API_KEY;

    public WeatherServiceApi(WebClient webClient, WeatherRepository weatherRepository) {
        this.webClient = webClient;
        this.weatherRepository = weatherRepository;
    }

    public void retrieveDataFromApi(String city) {
        try {
            String apiUrlWithParams = API_URL + "?q=" + city;

            String responseBody = webClient.get()
                    .uri(apiUrlWithParams)
                    .header("X-RapidAPI-Key", API_KEY)
                    .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper objectMapper = new ObjectMapper();
            WeatherDataDto weatherDataDto = objectMapper.readValue(responseBody, WeatherDataDto.class);
            dataConversionAndSummarization(weatherDataDto);
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON response from API", e);
        } catch (
                WebClientResponseException.NotFound notFoundException) {
            log.error("Data not found for the specified city: {}", city);
        } catch (Exception e) {
            log.error("An unexpected error occurred while retrieving data from API", e);
        }
    }


    public void dataConversionAndSummarization(WeatherDataDto weatherDataDto) {
        try {
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
            log.info("Weather data saved to DB: {}", weather);
        } catch (Exception e) {
            log.error("An unexpected error occurred during data conversion and summarization", e);
        }
    }
}


