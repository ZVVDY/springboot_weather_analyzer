package com.example.springboot_weather_analyzer.api;

import com.example.springboot_weather_analyzer.entity.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherServiceApi {
    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final WebClient webClient;


    public WeatherServiceApi(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(apiUrl)
                .defaultHeader("X-RapidAPI-Key", apiKey)
                .build();
    }

    public Mono<Weather> getWeatherData(String city) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("q", city).build())
                .retrieve()
                .bodyToMono(Weather.class);
    }
}


