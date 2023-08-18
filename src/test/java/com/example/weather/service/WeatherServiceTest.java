package com.example.weather.service;

import com.example.weather.exception.HttpResponseException;
import com.example.weather.persistence.dao.WeatherDescriptionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {
    private static final OkHttpClient httpClient = new OkHttpClient().newBuilder().build();
    private static final WeatherService weatherService = new WeatherService(httpClient, new ObjectMapper(),
            mock(WeatherDescriptionRepository.class));
    private static final String apiKey = "placeholder";

    @Disabled // put real apiKey in then reenable
    @Test
    void getGeocodeViaApi() throws IOException {
        String[] geocode;
        try {
            geocode = weatherService.getGeocodeViaApi("melbourne", "aus", apiKey);
        } catch (HttpResponseException e) {
            throw new RuntimeException(e);
        }
        assertEquals("-37.8142176", geocode[0]); // lat
        assertEquals("144.9631608", geocode[1]); // lon
    }

    @Test
    void getGeocodeViaApi_Fails() {
        HttpResponseException ex = assertThrows(HttpResponseException.class,
                () -> weatherService.getGeocodeViaApi("Melbourne", "aus", "invalidApiKey"));
        assertEquals(401, ex.getResponseCode());
    }

    @Disabled // put real apiKey in then reenable
    @Test
    void getWeatherDescriptionViaApi() throws IOException {
        String weatherDescription;
        try {
            weatherDescription = weatherService.getWeatherDescriptionViaApi("-37.8142176", "144.9631608", apiKey);
        } catch (HttpResponseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Weather description: " + weatherDescription);
        assertFalse(weatherDescription.isEmpty());
    }
}