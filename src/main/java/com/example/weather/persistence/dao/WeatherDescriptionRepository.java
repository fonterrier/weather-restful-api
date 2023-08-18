package com.example.weather.persistence.dao;

import com.example.weather.persistence.model.WeatherDescription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherDescriptionRepository extends CrudRepository<WeatherDescription, Long> {
    @Query("SELECT w FROM WeatherDescription w WHERE w.city=?1 AND w.country=?2")
    List<WeatherDescription> findByCityAndCountry(String city, String country);
}