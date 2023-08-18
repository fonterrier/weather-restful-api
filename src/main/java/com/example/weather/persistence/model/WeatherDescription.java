package com.example.weather.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "weather_description",
        indexes= {
                @Index(name = "getIndex", columnList = "city, country")
        }, uniqueConstraints = {
        @UniqueConstraint(columnNames = { "city", "country" })
})
public class WeatherDescription {

    @Id
    @GeneratedValue
    private Long id;

    // lowercase
    @Column(name = "city", nullable = false)
    private String city;
    // lowercase
    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "weather", nullable = false)
    private String weatherDescription;

    public WeatherDescription(String city, String country, String weatherDescription) {
        this.city = city;
        this.country = country;
        this.weatherDescription = weatherDescription;
    }

    public WeatherDescription() {

    }
}