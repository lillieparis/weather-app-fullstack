package com.example.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class WeatherActivityService {

    private final Map<String, List<String>> weatherActivities;

    public WeatherActivityService() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

         weatherActivities = objectMapper.readValue(
                 new ClassPathResource("weather_activity.json").getFile(),Map.class);
    }

    public String getWeatherActivity(String weatherDescription){
        List<String> activities = weatherActivities.getOrDefault(weatherDescription, Collections.emptyList());
        return activities.isEmpty() ? "No suggestions available" : activities.get(new Random().nextInt(activities.size()));

    }
}
