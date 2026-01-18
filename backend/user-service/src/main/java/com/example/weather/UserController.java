package com.example.weather;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

//slf4j is for printing span and trace id

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;



    //USER
    @Operation(
            summary = "Gets user by id",
            description = "This method gets the user by their generated and unique id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "user found by id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "user not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/users/{id}")
    User getUserById(@PathVariable Long id){

        return userService.getUserById(id);
    }

    @Operation(
            summary = "Gets all users",
            description = "This method gets all the users"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "users found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "no users found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/users")
    List<User> allUsers(){
        return userService.getALlUsers();

    }


    @Operation(
            summary = "Gets user by username",
            description = "This method gets the user by their username"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "user found by username",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "user not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/users/username/{username}")
    User getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);

    }


    @Operation(
            summary = "Adds new user",
            description = "This method add a new user via RequestBody"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "user found by id",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "user not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping("/users/create-account")
    User newUser(@RequestBody User newUser){
        return userService.saveUser(newUser);
    }


    @Operation(
            summary = "adds a location to the user via their id",
            description = "This method adds a new saved location to the user associated with PathVariable id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "new location added",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "user not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping("/users/add-location/{id}")
    User addLocationForUser(@PathVariable Long id, @RequestBody User newLocationUser){
        return userService.updateUser(newLocationUser, id);
    }


    @Operation(
            summary = "deletes a location to the user via their username",
            description = "This method deletes a location to the user associated with PathVariables username, lat, lon"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "location deleted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "user not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping("users/delete-location/{username}/{lat}/{lon}")
    void deleteUserLocation(@PathVariable String username,
                            @PathVariable Double lat,
                            @PathVariable Double lon){
        userService.deleteUserLocation(username, lat,lon);
    }


    @Operation(
            summary = "adds a location to the user via their username",
            description = "This method adds a new saved location to the user associated with PathVariable username"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "new location added",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "user not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("users/saved-locations/{username}")
    Set<Coordinate> getUserLocations(@PathVariable String username){
        return userService.getUserLocations(username);
    }


    @Operation(
            summary = "update user by id",
            description = "This method updates the user associated with PathVariable id via RequestBody"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "user updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "user not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id){
        return userService.updateUser(newUser,id);
    }


    @Operation(
            summary = "deletes user via their id",
            description = "This method deletes the user associated with PathVariable id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "user deleted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "user not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
    }

}
