package com.example.springboot_weather_analyzer.api.util;

import com.example.springboot_weather_analyzer.api.service.WeatherServiceApi;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class WeatherScheduler {
    private WeatherServiceApi weatherServiceApi;

    @Value("${weather.api.city}")
    private String NAME_CITY;
    public WeatherScheduler(WeatherServiceApi weatherServiceApi) {
        this.weatherServiceApi = weatherServiceApi;
    }

    @PostConstruct
    public void simulateSensorData() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> scheduledFuture = executorService.scheduleAtFixedRate(() -> {
            try {
                weatherServiceApi.retrieveDataFromApi(NAME_CITY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.MINUTES);

        if (executorService instanceof ScheduledThreadPoolExecutor) {
            ScheduledThreadPoolExecutor stpe = (ScheduledThreadPoolExecutor) executorService;
            Runtime.getRuntime().addShutdownHook(new Thread(stpe::shutdown));
        }
    }

}
