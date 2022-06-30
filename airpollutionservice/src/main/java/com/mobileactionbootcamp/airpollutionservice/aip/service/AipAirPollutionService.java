package com.mobileactionbootcamp.airpollutionservice.aip.service;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.internal.JsonContext;
import com.mobileactionbootcamp.airpollutionservice.aip.model.AipAirPollution;
import com.mobileactionbootcamp.airpollutionservice.aip.model.Components;
import com.mobileactionbootcamp.airpollutionservice.geo.model.GeoGeocoding;
import com.mobileactionbootcamp.airpollutionservice.geo.service.GeoGeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AipAirPollutionService {

    private final WebClient.Builder webClientBuilder;
    private final GeoGeocodingService geoGeocodingService;

    @Value("${air-pollution-api-key}")
    private String API_KEY;

    private GeoGeocoding geoGeocoding;

    public List<Components> getHistoricalAirPollutionData(String location, Date start, Date end){

        geoGeocoding = geoGeocodingService.getCoordinatesByLocationName(location);

        long startTime = convertToUnixDate(start);
        long endTime = convertToUnixDate(end);
        String json = makeWeatherApiRequest(geoGeocoding.getLat(), geoGeocoding.getLon(), startTime, endTime);

        List<Components> componentsList = parseJsonComponents(json);
        //Components components = parseAverage(componentsList);

        return componentsList;
    }

   /* private Components parseAverage(String json){

        Components components = JsonPath.p



        return null;
    }*/
    private List<Components> parseJsonComponents(String json){

        List<Components> componentsList = JsonPath.read(json, "$..components");
        return componentsList;
    }

    public String makeWeatherApiRequest(BigDecimal lat, BigDecimal lon, long startTime, long endTime){

        String json = webClientBuilder
                .baseUrl("http://api.openweathermap.org/data/2.5/air_pollution")
                .build()
                .get()
                .uri("/history?lat=" + geoGeocoding.getLat() + "&lon=" + geoGeocoding.getLon() + "&start=" + startTime + "&end=" + endTime + "&appid=" + API_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return json;
    }

    private long convertToUnixDate(Date date){
        long unixTime = date.getTime() / 1000L;
        return unixTime;
    }
}
