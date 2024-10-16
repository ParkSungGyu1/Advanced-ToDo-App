package com.sparta.todoapp.external.api.weather.client;

import com.sparta.todoapp.external.api.weather.dto.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WeatherClient {

    private final RestTemplate restTemplate;

    public List<WeatherResponseDto> getWeather() {
        String url = "https://f-api.github.io/f-api/weather.json";

        ResponseEntity<List<WeatherResponseDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Error: " + response.getStatusCode());
        }


/*        Optional<WeatherResponseDto> filteredWeather = response.getBody().stream()
                .filter(weather -> "01-02".equals(weather.getDate()))
                .findFirst();*/

        return response.getBody();
    }
}
