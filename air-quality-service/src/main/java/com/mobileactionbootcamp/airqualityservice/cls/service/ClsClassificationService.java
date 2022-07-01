package com.mobileactionbootcamp.airqualityservice.cls.service;


import com.mobileactionbootcamp.airqualityservice.cls.model.ClsCategories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ClsClassificationService {
    private final WebClient.Builder webClientBuilder;

    public ClsCategories getAqiClassification(String location, String start, String end){

        ClsCategories clsCategories = webClientBuilder
                .baseUrl("http://localhost:8083/classification/api/v1")
                .build()
                .get()
                .uri("/aqi/" + location + "/" + start + "/" + end)
                .retrieve()
                .bodyToMono(ClsCategories.class)
                .block();

        return clsCategories;
    }
}
