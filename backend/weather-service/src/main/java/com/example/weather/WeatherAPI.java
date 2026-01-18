package com.example.weather;

//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Component
public class WeatherAPI {

    private final String apiKey = "f124393b283d4862b3b120742251902";
    private final String baseUrl = "https://api.weatherapi.com/v1/current.json";

    @Autowired
    private final RestTemplate regularRestTemplate;

    public WeatherAPI(@Qualifier("regularRestTemplate") RestTemplate regularRestTemplate) {
        this.regularRestTemplate = regularRestTemplate;
    }


    public Weather getWeatherByLatLon(Double lat, Double lon){
        String url = String.format("%s?key=%s&q=%s,%s&aqi=no", baseUrl, apiKey, lat, lon);

        Map<String, Object> jsonMap = regularRestTemplate.getForObject(url, Map.class);

        return mapToWeather(jsonMap);


    }






    //maps json response to a Weather object
    public Weather mapToWeather(Map<String,Object> jsonResponse){
        Weather newWeather = new Weather();
        newWeather.setLocationName((String) ((Map<String,Object>) jsonResponse.get("location")).get("name"));
        newWeather.setTemperatureF(((Double)( (Map<String,Object>) jsonResponse.get("current")).get("temp_f")));
        newWeather.setTemperatureC(((Double)( (Map<String,Object>) jsonResponse.get("current")).get("temp_c")));
        newWeather.setLongitude(((Double)((Map<String,Object>) jsonResponse.get("location")).get("lon")));
        newWeather.setLatitude(((Double)((Map<String,Object>) jsonResponse.get("location")).get("lat")));
        String weatherDescription = (String) ((Map<String,Object>) ((Map<String,Object>) jsonResponse
                .get("current"))
                .get("condition"))
                .get("text");
        String weatherIcon = (String) ((Map<String,Object>) ((Map<String,Object>) jsonResponse
                .get("current"))
                .get("condition"))
                .get("icon");
        newWeather.setWeather(weatherDescription);
        newWeather.setImage(weatherIcon);

        return newWeather;
    }



}
