package com.example.weather.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerTest {
    private static final String WEATHER_PATH = "/weather/";

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    // happy path test
    @Test
    void getWeather() throws Exception {
        this.mockMvc.perform(
                        get(WEATHER_PATH)
                                .queryParam("city","Melbourne")
                                .queryParam("country","AUS"))
                .andExpect(status().is(200));
    }

    /**
     * Advantage of using OpenAPI generator is it automatically generates code to validate required
     * parameters, parameter types
     */
    @Test
    void getWeather_missingParameters_isBadRequest() throws Exception {
        this.mockMvc.perform(get(WEATHER_PATH)).andExpect(status().is(400));
        this.mockMvc.perform(get(WEATHER_PATH).queryParam("city","Melbourne")).andExpect(status().is(400));
        this.mockMvc.perform(get(WEATHER_PATH).queryParam("country","AUS")).andExpect(status().is(400));
    }
}