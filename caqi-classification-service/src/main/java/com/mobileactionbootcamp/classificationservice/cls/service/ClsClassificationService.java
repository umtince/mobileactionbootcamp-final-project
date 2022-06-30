package com.mobileactionbootcamp.classificationservice.cls.service;

import com.mobileactionbootcamp.classificationservice.aip.model.Components;
import com.mobileactionbootcamp.classificationservice.aip.service.AipAirPollutionService;
import com.mobileactionbootcamp.classificationservice.cls.model.ClsCategories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ClsClassificationService {

    private int LESS = -1;
    private int EQUAL = 0;
    private int MORE = 1;
    private final AipAirPollutionService aipAirPollutionService;

    public ClsCategories getAqiResults(String location, Date start , Date end){

        Components components = aipAirPollutionService.getHistoricalAirPollutionData(location, start, end);



        return null;
    }

    private String evaluateCoLevels(BigDecimal co){

        co = co.setScale(1, RoundingMode.HALF_UP);
        String result = "";

        if(co.compareTo(BigDecimal.valueOf(1.1)) == LESS){
            result = "Good";
        } else if (co.compareTo(BigDecimal.valueOf(1.1)) == EQUAL || co.compareTo(BigDecimal.valueOf(2.1)) == LESS) {
            result = "Satisfactory";
        } else if (co.compareTo(BigDecimal.valueOf(2.1)) == EQUAL || co.compareTo(BigDecimal.valueOf(10)) == LESS) {
            result = "Moderate";
        } else if (co.compareTo(BigDecimal.valueOf(17)) == EQUAL || co.compareTo(BigDecimal.valueOf(17)) == LESS) {
            result = "Poor";
        } else if (co.compareTo(BigDecimal.valueOf(34)) == EQUAL || co.compareTo(BigDecimal.valueOf(34)) == LESS) {
            result = "Severe";
        } else if (co.compareTo(BigDecimal.valueOf(34)) == MORE) {
            result = "Hazardous";
        }

        return result;
    }

    /*private String evaluateSo2Levels(BigDecimal so2){

    }

    private String evaluateO3Levels(BigDecimal o3){

    }*/



}
