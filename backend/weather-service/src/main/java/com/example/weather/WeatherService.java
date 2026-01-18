package com.example.weather;

//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//interacts with weather db directly
@Service
public class WeatherService {

    @Autowired
    private final RestTemplate regularRestTemplate;

    @Autowired
    private RestTemplate lbRestTemplate;

    public WeatherService(RestTemplate regularRestTemplate, RestTemplate lbRestTemplate){
        this.regularRestTemplate = regularRestTemplate;
        this.lbRestTemplate = lbRestTemplate;
    }

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private WeatherAPI weatherAPI;
    public Weather getWeatherById(Long id) {
        return weatherRepository.findById(id).get();
    }



    public Weather updateWeatherById(Long id){
        Weather currentWeather =  weatherRepository.findById(id).get();
        Double lat = currentWeather.getLatitude();
        Double lon = currentWeather.getLongitude();

        Weather updatedWeather = weatherAPI.getWeatherByLatLon(lat, lon);

        return weatherRepository.findById(id)
                .map(weather -> {
                    weather.setLocationName(updatedWeather.getLocationName());
                    weather.setTemperatureF(updatedWeather.getTemperatureF());
                    weather.setTemperatureC(updatedWeather.getTemperatureC());
                    weather.setWeather(updatedWeather.getWeather());
                    weather.setLatitude(updatedWeather.getLatitude());
                    weather.setLongitude(updatedWeather.getLongitude());

                    return weatherRepository.save(weather);
                })
                .orElseGet(() ->{
                    return weatherRepository.save(updatedWeather);
                });


    }


    public List<Weather> getAllWeather() {
        return weatherRepository.findAll();
    }

    @CircuitBreaker(name = "getWeather", fallbackMethod = "fallBackWeather")
    public Weather addWeather(Double lat, Double lon) {
        return weatherRepository.save(weatherAPI.getWeatherByLatLon(lat, lon));
    }

    public String fallBackWeather(Throwable throwable){
        return "fallback location";

    }

    public void deleteWeatherById(Long id) {
        weatherRepository.deleteById(id);
    }


    @CircuitBreaker(name="weatherService",fallbackMethod = "fallBackUserWeather")
    public List<Weather> getWeatherByUser(String username) {
        List<Weather> weatherToReturn = new ArrayList<>();
        String url = String.format("lb://USER-SERVICE/users/username/%s", username);

        Map<String, Object> jsonMapUser = lbRestTemplate.getForObject(url, Map.class);


        if (jsonMapUser == null) {
            throw new RuntimeException("User not found");
        }

        UserDTO user = mapToUserDTO(jsonMapUser);

         List<Double> lat = user.getUserSavedLats();
         List<Double> lon = user.getUserSavedLons();

         WeatherAPI weatherAPI1 = new WeatherAPI(regularRestTemplate);

         for(int i = 0; i < lat.size();i++ ){
             weatherToReturn.add(weatherRepository.save(weatherAPI1.getWeatherByLatLon(lat.get(i),lon.get(i))));
         }

         return weatherToReturn;

    }

    public String fallBackUserWeather(String username, Throwable throwable){
        return "fallback get weather for user with username" + username;
    }

    public UserDTO mapToUserDTO(Map<String, Object> jsonResponse){
        UserDTO newUser = new UserDTO();
        newUser.setUsername((String) jsonResponse.get("username"));
        newUser.setPassword((String) jsonResponse.get("password"));
        newUser.addUserSavedLat((List<Double>) jsonResponse.get("userSavedLats"));
        newUser.addUserSavedLon((List<Double>) jsonResponse.get("userSavedLons"));



        return newUser;

    }


    }



