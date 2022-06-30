package com.mobileactionbootcamp.airpollutionservice.geo.service;

import com.mobileactionbootcamp.airpollutionservice.geo.model.GeoGeocoding;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GeoGeocodingService {

    private final WebClient.Builder webClientBuilder;

    public GeoGeocoding getCoordinatesByLocationName(String location){

        GeoGeocoding geoGeocoding = webClientBuilder
                .baseUrl("http://localhost:8081")
                .build()
                .get()
                .uri("/geocoding-api/v1/location/" + location)
                .retrieve()
                .bodyToMono(GeoGeocoding.class)
                .block();
        return geoGeocoding;
    }
}
