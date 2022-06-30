package com.mobileactionbootcamp.airpollutionservice.aip.service;

import com.mobileactionbootcamp.airpollutionservice.aip.model.AipAirPollution;
import com.mobileactionbootcamp.airpollutionservice.geo.model.GeoGeocoding;
import com.mobileactionbootcamp.airpollutionservice.geo.service.GeoGeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AipAirPollutionService {

    private final WebClient.Builder webClientBuilder;
    private final GeoGeocodingService geoGeocodingService;

    @Value("${air-pollution-api-key}")
    private String API_KEY;

    private GeoGeocoding geoGeocoding;

    public AipAirPollution getHistoricalAirPollutionData(String location, Date start, Date end){

        geoGeocoding = geoGeocodingService.getCoordinatesByLocationName(location);
        long startTime = convertToUnixDate(start);
        long endTime = convertToUnixDate(end);

        AipAirPollution aipAirPollution = webClientBuilder
                .baseUrl("http://api.openweathermap.org/data/2.5/air_pollution")
                .build()
                .get()
                .uri("/history?lat=" + geoGeocoding.getLat() + "&lon=" + geoGeocoding.getLon() + "&start=" + startTime + "&end=" + endTime + "&appid=" + API_KEY)
                .retrieve()
                .bodyToMono(AipAirPollution.class)
                .block();

        return aipAirPollution;
    }

    private long convertToUnixDate(Date date){
        long unixTime = date.getTime() / 1000L;
        return unixTime;
    }
}
