package com.example.weather.controller;

import com.example.weather.api.model.Weather;
import com.example.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

/**
 * Spring Controller layer - handle http logic
 */
@RequiredArgsConstructor
@Controller
public class WeatherController implements com.example.weather.api.api.WeatherApiDelegate {
    // autowired beans
    private final WeatherService weatherService;

    @Value("${com.example.weather.openweather.api.keys}")
    private String[] openWeatherApiKeys;

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
    // call, e.g. from command line: curl -X GET "http://localhost:8080/weather?city=Melbourne&country=AUS"
    @Override
    public ResponseEntity<com.example.weather.api.model.Weather> getWeather(String city, String country) {
        try {
            // OpenAPI generated code has already validated city and country both present (required)
            // and returned 400 if not.
            String weatherDesc = weatherService.getWeather(city.toLowerCase(), country.toLowerCase(), openWeatherApiKeys[0]);

            Weather weatherDto = new Weather();
            weatherDto.setDescription(weatherDesc);
            return new ResponseEntity<>(weatherDto, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}