package com.sparta.todoapp.external.api.weather.controller;

import com.sparta.todoapp.external.api.weather.client.WeatherClient;
import com.sparta.todoapp.external.api.weather.dto.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherClient weatherClient;

    @GetMapping("/api/weather")
    public ResponseEntity<List<WeatherResponseDto>> getWeather(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(weatherClient.getWeather());
    }
}
