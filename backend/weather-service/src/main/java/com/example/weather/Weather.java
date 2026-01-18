package com.example.weather;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Weather {


    private @Id
    @GeneratedValue Long iD;

    String locationName;

    Double temperatureF;

    Double temperatureC;

    String weather;

    String image;

    Double latitude;

    Double longitude;

    public Weather(String locationName, Double temperatureF,Double temperatureC, String weather, Double latitude,
                   Double longitude) {
        this.locationName = locationName;
        this.temperatureF = temperatureF;
        this.temperatureC = temperatureC;
        this.weather = weather;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Weather(String locationName, Double temperatureF, String weather, String image, Double latitude, Double longitude) {
        this.locationName = locationName;
        this.temperatureF = temperatureF;
        this.weather = weather;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getTemperatureF() {
        return temperatureF;
    }

    public void setTemperatureF(Double temperatureF) {
        this.temperatureF = temperatureF;
    }

    public Double getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(Double temperatureC) {
        this.temperatureC = temperatureC;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Long getiD() {
        return iD;
    }
}
