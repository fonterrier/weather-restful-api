package com.example.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherApplicationConfig {
    @Bean
    OkHttpClient httpClient() {
        //Create httpClient or build or get it however you want and return
        return new OkHttpClient().newBuilder().build();
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}