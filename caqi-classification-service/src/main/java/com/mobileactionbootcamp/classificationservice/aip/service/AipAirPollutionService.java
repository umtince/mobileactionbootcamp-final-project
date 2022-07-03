package com.mobileactionbootcamp.classificationservice.aip.service;

import com.mobileactionbootcamp.classificationservice.aip.model.AipDailyComponents;
import com.mobileactionbootcamp.classificationservice.aip.model.AipDailyComponentsWrapper;
import com.mobileactionbootcamp.classificationservice.aip.model.Components;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AipAirPollutionService {

    private final WebClient.Builder webClientBuilder;

    public AipDailyComponentsWrapper getHistoricalAirPollutionData(String location, String start, String end){

        AipDailyComponentsWrapper aipDailyComponentsWrapper = webClientBuilder
                .baseUrl("http://localhost:8082")
                .build()
                .get()
                .uri("/air-pollution-api/v1/historical/" + location + "/" + start + "/" + end)
                .retrieve()
                .bodyToMono(AipDailyComponentsWrapper.class)
                .block();

        return aipDailyComponentsWrapper;
    }

}

