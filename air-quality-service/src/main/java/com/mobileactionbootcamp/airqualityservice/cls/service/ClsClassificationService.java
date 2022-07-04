package com.mobileactionbootcamp.airqualityservice.cls.service;


import com.mobileactionbootcamp.airqualityservice.cls.model.ClsCategories;
import com.mobileactionbootcamp.airqualityservice.cls.model.ClsCategoriesWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ClsClassificationService {
    private final WebClient.Builder webClientBuilder;

    public ClsCategoriesWrapper getAqiClassification(String location, String start, String end){

        ClsCategoriesWrapper clsCategoriesWrapper = webClientBuilder
                .baseUrl("http://localhost:8083/classification/api/v1")
                .build()
                .get()
                .uri("/aqi/" + location + "/" + start + "/" + end)
                .retrieve()
                .bodyToMono(ClsCategoriesWrapper.class)
                .block();

        return clsCategoriesWrapper;
    }
}
