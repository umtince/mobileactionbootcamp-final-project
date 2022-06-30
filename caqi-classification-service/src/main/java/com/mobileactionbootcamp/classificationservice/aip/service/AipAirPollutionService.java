package com.mobileactionbootcamp.classificationservice.aip.service;

import com.mobileactionbootcamp.classificationservice.aip.model.Components;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AipAirPollutionService {

    private final WebClient.Builder webClientBuilder;

    public Components getHistoricalAirPollutionData(String location, Date start, Date end){

        Components components = webClientBuilder
                .baseUrl("http://localhost:8082")
                .build()
                .get()
                .uri("/air-pollution-api/v1/historical/" + location + "/" + start + "/" + end)
                .retrieve()
                .bodyToMono(Components.class)
                .block();

        return components;
    }

}

