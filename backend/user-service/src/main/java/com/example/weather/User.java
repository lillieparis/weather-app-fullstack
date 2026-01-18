package com.example.weather;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
public class User {

    @Getter
    private @Id
    @GeneratedValue Long id;

    @Getter
    String username;

    @Getter
    String password;

//    @ElementCollection
//    List<Double> userSavedLats = new ArrayList<>();
//
//
//    @ElementCollection
//    List<Double> userSavedLons = new ArrayList<>();

    @ElementCollection
    Set<Coordinate> savedCoordinates = new HashSet<>();

    public User(String username, String password, Double lat, Double lon) {
        this.username = username;
        this.password = password;
        Coordinate coordinate = new Coordinate(lat, lon);
        savedCoordinates.add(coordinate);
//        this.userSavedLats.add(lat) ;
//        this.userSavedLons.add(lon);
    }

//    public List<Double> getUserSavedLats() {
//        return userSavedLats;
//    }
//
//    public List<Double> getUserSavedLons() {
//        return userSavedLons;
//    }

    public Set<Coordinate> getSavedCoordinates() {
        return savedCoordinates;
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

//    public void setUserSavedLat(Double userSavedLat) {
//        this.userSavedLats.add(userSavedLat);
//    }
//
//    public void setUserSavedLon(Double userSavedLon) {
//        this.userSavedLons.add(userSavedLon);
//    }

//    public void setUserSavedLats(List<Double> userSavedLat) {
//
//        this.userSavedLats.addAll(userSavedLat);
//
//    }
//
//    public void setUserSavedLons(List<Double> userSavedLon) {
//        this.userSavedLons.addAll(userSavedLon);
//        setSavedCoordinates(this.userSavedLats,this.userSavedLons);
//    }

    public void setSavedCoordinates(Set<Coordinate> coordinates) {
        savedCoordinates.addAll(coordinates);
    }

}

//
//    public void addUserSavedLat(Double userSavedLat){
//        this.userSavedLats.add(userSavedLat);
//    }
//
//    public void addUserSavedLon(Double userSavedLon){
//        this.userSavedLons.add(userSavedLon);
//    }
//}
