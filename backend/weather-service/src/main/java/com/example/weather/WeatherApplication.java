package com.example.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WeatherApplication {

        public static void main(String[] args) {
            SpringApplication.run(WeatherApplication.class, args);


    }

    @Bean
    @LoadBalanced
    RestTemplate lbRestTemplate(){
            return new RestTemplate();
    }

    // Regular RestTemplate for external APIs
    @Bean
    public RestTemplate regularRestTemplate() {
        return new RestTemplate();
    }

}
