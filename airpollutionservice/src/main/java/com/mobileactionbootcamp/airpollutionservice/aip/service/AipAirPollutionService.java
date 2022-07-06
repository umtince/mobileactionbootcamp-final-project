package com.mobileactionbootcamp.airpollutionservice.aip.service;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.mobileactionbootcamp.airpollutionservice.aip.model.AipDailyComponents;
import com.mobileactionbootcamp.airpollutionservice.aip.model.AipDailyComponentsWrapper;
import com.mobileactionbootcamp.airpollutionservice.aip.model.Components;
import com.mobileactionbootcamp.airpollutionservice.geo.model.GeoGeocoding;
import com.mobileactionbootcamp.airpollutionservice.geo.service.GeoGeocodingService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AipAirPollutionService {

    private final WebClient.Builder webClientBuilder;
    private final GeoGeocodingService geoGeocodingService;

    @Value("${air-pollution-api-key}")
    private String API_KEY;

    private GeoGeocoding geoGeocoding;

    public AipDailyComponentsWrapper getHistoricalAirPollutionData(String location, LocalDate start, LocalDate end){

        geoGeocoding = geoGeocodingService.getCoordinatesByLocationName(location);

        long startTime = convertToUnixDate(start);
        long endTime = convertToUnixDate(end);
        String json = makeWeatherApiRequest(geoGeocoding.getLat(), geoGeocoding.getLon(), startTime, endTime);

        ReadContext ctx = JsonPath.parse(json);

        List<AipDailyComponents> aipDailyComponentsList = parseDailyJsonComponents(ctx);
        AipDailyComponentsWrapper aipDailyComponentsWrapper = new AipDailyComponentsWrapper();
        aipDailyComponentsWrapper.setAipDailyComponentsList(aipDailyComponentsList);

        return aipDailyComponentsWrapper;
    }

    private List<AipDailyComponents> parseDailyJsonComponents(ReadContext json){

        JSONArray jsonComponentsArray = json.read( "$.list[*]");
        int jsonComponentNumber = jsonComponentsArray.size();
        int dtStart = 0;
        int dtEnd = 0;
        String startDate = "";
        String endDate = "";
        int startIndex;
        int endIndex = 0;
        List<AipDailyComponents> aipDailyComponentsList = new ArrayList<>();
        AipDailyComponents aipDailyComponents;

        dtStart = json.read( "$.list[0].dt");
        startDate = convertToLocalDateString(dtStart);
        startIndex = 0;
        for(int i=0; i<jsonComponentNumber; i++){

            dtEnd = json.read( "$.list["+i+"].dt");
            endDate = convertToLocalDateString(dtEnd);
            endIndex++;
            if(startDate.equals(endDate)){
                continue;
            }
            else {
                aipDailyComponents = new AipDailyComponents();
                aipDailyComponents.setDate(startDate);
                aipDailyComponents.setComponents(parseAverageJsonComponents(json.read( "$.list["+startIndex+":"+endIndex+"]")));

                aipDailyComponentsList.add(aipDailyComponents);

                dtStart = json.read( "$.list["+i+"].dt");
                startDate = convertToLocalDateString(dtStart);
                startIndex = i;
            }
        }

        return aipDailyComponentsList;
    }



    private Components parseAverageJsonComponents(JSONArray jsonArray){

        String json = jsonArray.toJSONString();
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

    private String convertToLocalDateString(long epoch){

        Instant instant = Instant.ofEpochSecond( epoch );
        return parseDateToString(LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate());
    }

    private String parseDateToString(LocalDate date){
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(DATE_FORMATTER);
    }

    private long convertToUnixDate(LocalDate date){
        long epoch = date.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC);
        return epoch;
    }
}
