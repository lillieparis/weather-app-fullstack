package com.example.weather;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherActivityService weatherActivityService;

    //WEATHER
    @Operation(
            summary = "Get all saved weather",
            description = "This method returns all the weather saved"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Weather found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Weather.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Weather not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/weather")
    List<Weather> allWeather() {
        return weatherService.getAllWeather();
    }


    @Operation(
            summary = "Get weather saved for id",
            description = "This method returns the weather associated and saved with the PathVariable id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Weather id found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Weather.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Weather not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/weather/{id}")
    Weather getWeatherById(@PathVariable Long id) {
        return weatherService.getWeatherById(id);
    }



    @Operation(
            summary = "Put user's weather",
            description = "This method puts and returns the weather associated with the user's saved locations"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "user's weather successfully put",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Weather.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Weather not found nor put",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PutMapping("/weather/user/{username}")
    List<Weather> getWeatherByUser(@PathVariable String username){
        return weatherService.getWeatherByUser(username);
    }


    //only these two cause interaction with the api
    @Operation(
            summary = "Put weather for id",
            description = "This method puts and returns the weather associated and saved with the Pathvariable id," +
                    "making a new api call to the Weather API"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Weather id found and updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Weather.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Weather not updated for id",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PutMapping("/weather/{id}")
    Weather updateWeatherById(@PathVariable Long id) {
        return weatherService.updateWeatherById(id);
    }


    @Operation(
            summary = "Add new weather by latitude and longitude",
            description = "This method returns and adds weather by latitude and longitude in PathVariable"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Weather for lat and long successfully added",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Weather.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Weather not able to be added",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping("/weather/{lat}/{lon}")
    Weather addWeather(@PathVariable Double lat, @PathVariable Double lon) {
        return weatherService.addWeather(lat, lon);
    }

    @Operation(
            summary = "Delete weather saved for id",
            description = "This method deletes the weather associated and saved with the PathVariable id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Weather id deleted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Weather.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Weather not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping("/weather/{id}")
    void deleteWeatherById(@PathVariable Long id) {
        weatherService.deleteWeatherById(id);
    }


    @GetMapping("/weather/activity/{weatherDescription}")
    String getWeatherActivityByWeatherDescription(@PathVariable String weatherDescription){
        return weatherActivityService.getWeatherActivity(weatherDescription);
    }
}


