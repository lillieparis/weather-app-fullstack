package com.example.weather;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    Long id;


    String username;


    String password;


    List<Double> userSavedLats = new ArrayList<>();


    List<Double> userSavedLons = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Double> getUserSavedLats() {
        return userSavedLats;
    }

    public List<Double> getUserSavedLons() {
        return userSavedLons;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserSavedLats(List<Double> userSavedLats) {
        this.userSavedLats.addAll(userSavedLats);
    }

    public void setUserSavedLons(List<Double> userSavedLon) {
        this.userSavedLons.addAll(userSavedLon) ;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userSavedLat=" + userSavedLats +
                ", userSavedLon=" + userSavedLons +
                '}';
    }


    public void addUserSavedLat(List<Double> userSavedLat) {
        this.userSavedLats.addAll(userSavedLat);
    }

    public void addUserSavedLon(List<Double> userSavedLon){
        this.userSavedLons.addAll(userSavedLon);
    }
}
