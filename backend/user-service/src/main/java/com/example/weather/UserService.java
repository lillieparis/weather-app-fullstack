package com.example.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User saveUser(User newUser) {
        return userRepository.save(newUser);

    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }


    public User updateUser(User newUser, Long id) {
        return userRepository.findById(id).
                map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    user.setSavedCoordinates(newUser.getSavedCoordinates());

                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    return userRepository.save(newUser);
                });

    }

    public void deleteUserById(Long iD){
        userRepository.deleteById(iD);
    }

    public List<User> getALlUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Set<Coordinate> getUserLocations(String username) {
        User user = userRepository.findByUsername(username);

//        List<Double> userLats = user.getUserSavedLats();
//        List<Double> userLons = user.getUserSavedLons();
//
//        Set<List<Double>> coordinates = new HashSet<>();
//
//        for(int i =0; i<userLats.size(); i++){
//            coordinates.add(List.of(userLats.get(i),userLons.get(i)));
//        }
//
//        return coordinates;

        return user.getSavedCoordinates();
    }

    public void deleteUserLocation(String username, Double lat, Double lon) {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            user.getSavedCoordinates().remove(new Coordinate(lat,lon));
            userRepository.save(user);
        }

    }
}
