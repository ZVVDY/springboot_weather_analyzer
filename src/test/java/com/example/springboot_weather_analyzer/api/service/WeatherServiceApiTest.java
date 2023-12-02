package com.example.springboot_weather_analyzer.api.service;

import com.example.springboot_weather_analyzer.api.dto.WeatherDataDto;
import com.example.springboot_weather_analyzer.entity.Weather;
import com.example.springboot_weather_analyzer.repository.WeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@WebFluxTest(WeatherServiceApi.class)
public class WeatherServiceApiTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private WeatherRepository weatherRepository;

    @MockBean
    private WebClient webClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeatherServiceApi weatherServiceApi;

    @Test
    public void testRetrieveDataFromApi() throws IOException {

        String mockApiResponse = "{ \"current\": { \"condition\": { \"text\": \"Clear\" } }, \"location\": { \"country\": \"US\", \"name\": \"New York\", \"region\": \"NY\", \"localtime\": \"2023-12-01 12:00\" } }";
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().setBody(mockApiResponse));
        String apiUrl = mockWebServer.url("/").toString();

        WebClient webClient = WebClient.builder().baseUrl(apiUrl).build();
        WeatherServiceApi weatherServiceApi = new WeatherServiceApi(webClient, weatherRepository);

        weatherServiceApi.retrieveDataFromApi("New York");
        verify(weatherRepository, times(1)).save(any(Weather.class));
        mockWebServer.shutdown();
    }

    @Test
    public void testDataConversionAndSummarization() throws JsonProcessingException {
        String mockApiResponse = "{ \"current\": { \"condition\": { \"text\": \"Clear\" }, \"humidity\": 50, \"pressure_mb\": 1010, \"temp_c\": 25, \"wind_kph\": 10 }, \"location\": { \"country\": \"US\", \"name\": \"New York\", \"region\": \"NY\", \"localtime\": \"2023-12-01 12:00\" } }";
        WeatherDataDto weatherDataDto = objectMapper.readValue(mockApiResponse, WeatherDataDto.class);
        weatherServiceApi.dataConversionAndSummarization(weatherDataDto);
        verify(weatherRepository, times(1)).save(any(Weather.class));
    }
}