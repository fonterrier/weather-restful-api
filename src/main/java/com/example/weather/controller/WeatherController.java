package com.example.weather.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class WeatherController implements com.example.weather.api.api.WeatherApiDelegate {

    /**
     * GET /weather : Get weather description at location
     *
     * @param city City name (required)
     * @param country Country code (ISO 3166 country codes) (required)
     * @return OK (status code 200)
     *         or Generic error response (status code 400)
     *         or Generic error response (status code 429)
     *         or Generic error response (status code 500)
     * @see WeatherApi#getWeather
     */
    @Override
    public ResponseEntity<com.example.weather.api.model.Weather> getWeather(String city, String country) {
        System.out.println("HELLO");
        return null;
    }
}