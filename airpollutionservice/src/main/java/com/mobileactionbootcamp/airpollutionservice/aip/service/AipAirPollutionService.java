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

    public Components getHistoricalAirPollutionData(String location, Date start, Date end){

        geoGeocoding = geoGeocodingService.getCoordinatesByLocationName(location);

        long startTime = convertToUnixDate(start);
        long endTime = convertToUnixDate(end);
        String json = makeWeatherApiRequest(geoGeocoding.getLat(), geoGeocoding.getLon(), startTime, endTime);

        Components components = parseAverageJsonComponents(json);

        return components;
    }

    private Components parseAverageJsonComponents(String json){

        Components dailyAverageComponents = new Components();

        dailyAverageComponents.setCo(BigDecimal.valueOf((Double) JsonPath.read(json, "$..components.co.avg()")));
        dailyAverageComponents.setNo(BigDecimal.valueOf((Double) JsonPath.read(json, "$..components.no.avg()")));
        dailyAverageComponents.setNo2(BigDecimal.valueOf((Double) JsonPath.read(json, "$..components.no2.avg()")));
        dailyAverageComponents.setO3(BigDecimal.valueOf((Double) JsonPath.read(json, "$..components.o3.avg()")));
        dailyAverageComponents.setSo2(BigDecimal.valueOf((Double) JsonPath.read(json, "$..components.so2.avg()")));
        dailyAverageComponents.setPm25(BigDecimal.valueOf((Double) JsonPath.read(json, "$..components.pm2_5.avg()")));
        dailyAverageComponents.setPm10(BigDecimal.valueOf((Double) JsonPath.read(json, "$..components.pm10.avg()")));
        dailyAverageComponents.setNh3(BigDecimal.valueOf((Double) JsonPath.read(json, "$..components.nh3.avg()")));

        return dailyAverageComponents;
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
