package com.example.weather.service;

import com.example.weather.exception.HttpResponseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import okhttp3.Request;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Spring Service layer - handle business logic
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService {
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String GEOCODE_URL = "http://api.openweathermap.org/geo/1.0/direct";

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    /**
     * Call the underlying OpenWeather API to get the weather description
     */
    public String getWeather(@NotNull String city, @NotNull String country, @NotNull String apiKey) throws HttpResponseException, IOException {
        // call OpenWeather GeoCoding API - get lat, lon for OpenWeather Weater API inputs
        String[] geocode = getGeocodeViaApi(city, country, apiKey);
        String lat = geocode[0];
        String lon = geocode[1];

        return getWeatherDescriptionViaApi(lat, lon, apiKey);
    }

    /**
     * Make GeoCode request to get lat,lon from OpenWeather API
     *
     * @return String[] - String[0] = lat, String[1] = lon
     * @throws HttpResponseException with code if OpenWeather API does not return 200
     */
    protected String[] getGeocodeViaApi(@NotNull String city, @NotNull String country, @NotNull String apiKey) throws HttpResponseException, IOException {
        log.info("Getting geocode via API for " + city + ", " + country);
        Request request = new Request.Builder()
                .url(GEOCODE_URL + "?q=" + city + "," + country + "&limit=1&appid=" + apiKey)
                .build();
        Call call = httpClient.newCall(request); // synchronous

        String responseBody;
        try (Response response = call.execute()) {
            // unsuccessful response
            if (response.code() != 200) {
                throw new HttpResponseException("Request not successful. Error code: " + response.code(), response.code());
            }

            // successful response
            // e.g.
            // [{"name":"Melbourne","local_names":{"eo":"Melburno","mk":"Мелбурн","is":"Melbourne","mr":"मेलबर्न","hi":"मॆल्बोर्न्","ru":"Мельбурн","lt":"Melburnas","uk":"Мельбурн","el":"Μελβούρνη","ko":"멜버른","en":"Melbourne","fr":"Melbourne","ja":"メルボルン","kn":"ಮೆಲ್ಬೋರ್ನ್","es":"Melbourne","sv":"Melbourne","zh":"墨爾本/墨尔本","de":"Melbourne","oc":"Melbourne","he":"מלבורן","fi":"Melbourne","ar":"ملبورن","sr":"Мелбурн","mi":"Poipiripi","pl":"Melbourne"},"lat":-37.8142176,"lon":144.9631608,"country":"AU","state":"Victoria"}]
            responseBody = response.body().string();
        }

        JsonNode entry = objectMapper.readTree(responseBody).get(0);
        String lat = entry.get("lat").asText();
        String lon = entry.get("lon").asText();

        return new String[] {lat, lon};
    }

    /**
     * Make Weather request to get description from OpenWeather API
     *
     * @return String description e.g. "moderate rain"
     * @throws HttpResponseException with code if OpenWeather API does not return 200
     */
    protected String getWeatherDescriptionViaApi(@NotNull String lat, @NotNull String lon, @NotNull String apiKey) throws HttpResponseException, IOException {
        log.info("Getting weather description via API for " + lat + ", " + lon);
        String url = WEATHER_URL + "?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = httpClient.newCall(request); // synchronous

        String responseBody;
        try (Response response = call.execute()) {
            // unsuccessful response
            if (response.code() != 200) {
                throw new HttpResponseException("Request not successful. Error code: " + response.code(), response.code());
            }

            // successful response
            // e.g.
            // {"coord":{"lon":144.9625,"lat":-37.8097},"weather":[{"id":500,"main":"Rain","description":"light rain","icon":"10d"}],"base":"stations","main":{"temp":283.51,"feels_like":282.72,"temp_min":282.04,"temp_max":284.79,"pressure":1006,"humidity":81},"visibility":10000,"wind":{"speed":10.8,"deg":250,"gust":15.95},"rain":{"1h":0.32},"clouds":{"all":75},"dt":1692318865,"sys":{"type":2,"id":2008797,"country":"AU","sunrise":1692306139,"sunset":1692344781},"timezone":36000,"id":2158177,"name":"Melbourne","cod":200}
            responseBody = response.body().string();
        }

        JsonNode entry = objectMapper.readTree(responseBody).get("weather").get(0).get("description");
        return entry.asText();

    }



}