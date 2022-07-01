package com.mobileactionbootcamp.airqualityservice.aqs.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AqsAirQualityService {

    private LocalDate CONTROL_DATE = LocalDate.of(2020,11,27);

    public void handleAirQualityRequest(String city, LocalDate start, LocalDate end) throws Exception{

        if(start.isBefore(CONTROL_DATE)){
            throw new Exception("Start date can not be before than November 27th 2020");
        }
        else if(end.isBefore(start)){
            throw new Exception("End date can not be before start date!");
        }



    }

    private String parseDateToString(LocalDate date){
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(DATE_FORMATTER);
    }
}
