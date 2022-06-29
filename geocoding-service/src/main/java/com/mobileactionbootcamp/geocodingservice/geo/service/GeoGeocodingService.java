package com.mobileactionbootcamp.geocodingservice.geo.service;

import com.mobileactionbootcamp.geocodingservice.geo.model.GeoGeocoding;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GeoGeocodingService {

    private final WebClient.Builder webClientBuilder;

    @Value("${geocoding-api-key}")
    private String API_KEY;
    private int RESPONSE_LIMIT = 1;

    public GeoGeocoding getCoordinatesByLocationName(String location){

        GeoGeocoding[] geoGeocoding = webClientBuilder
                .baseUrl("http://api.openweathermap.org")
                .build()
                .get()
                .uri("/geo/1.0/direct?" + "q=" + location + "&limit=" + RESPONSE_LIMIT + "&appid=" + API_KEY)
                .retrieve()
                .bodyToMono(GeoGeocoding[].class)
                .block();
        return geoGeocoding[0];
    }
}
