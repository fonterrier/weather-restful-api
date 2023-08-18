package com.example.weather.service;

import com.example.weather.exception.HttpResponseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Disabled // put real apiKey in then reenable
class WeatherServiceTest {
    private static final OkHttpClient httpClient = new OkHttpClient().newBuilder().build();
    private static final WeatherService weatherService = new WeatherService(httpClient, new ObjectMapper());
    private static final String apiKey = "placeholder";

    @Test
    void getGeocodeViaApi() throws IOException {
        String[] geocode;
        try {
            geocode = weatherService.getGeocodeViaApi("Melbourne", "AUS", apiKey);
        } catch (HttpResponseException e) {
            throw new RuntimeException(e);
        }
        assertEquals("-37.8142176", geocode[0]); // lat
        assertEquals("144.9631608", geocode[1]); // lon
    }

    @Test
    void getGeocodeViaApi_Fails() {
        HttpResponseException ex = assertThrows(HttpResponseException.class,
                () -> weatherService.getGeocodeViaApi("Melbourne", "AUS", "invalidApiKey"));
        assertEquals(401, ex.getResponseCode());
    }

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