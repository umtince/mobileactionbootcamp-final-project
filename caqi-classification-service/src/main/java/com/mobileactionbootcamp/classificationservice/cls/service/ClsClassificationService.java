package com.mobileactionbootcamp.classificationservice.cls.service;

import com.mobileactionbootcamp.classificationservice.aip.model.Components;
import com.mobileactionbootcamp.classificationservice.aip.service.AipAirPollutionService;
import com.mobileactionbootcamp.classificationservice.cls.model.ClsCategories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ClsClassificationService {

    private int LESS = -1;
    private int EQUAL = 0;
    private int MORE = 1;
    private final AipAirPollutionService aipAirPollutionService;

    public ClsCategories getAqiResults(String location, LocalDate start , LocalDate end){

        Components components = aipAirPollutionService.getHistoricalAirPollutionData(location, parseDateToString(start), parseDateToString(end));

        ClsCategories clsCategories = new ClsCategories();
        clsCategories.getCo().setCategory(evaluateCoLevels(components.getCo()));
        clsCategories.getSo2().setCategory(evaluateSo2Levels(components.getSo2()));
        clsCategories.getO3().setCategory(evaluateO3Levels(components.getO3()));

        return clsCategories;
    }

    private String parseDateToString(LocalDate date){
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(DATE_FORMATTER);
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

    private String evaluateSo2Levels(BigDecimal so2){
        so2 = so2.setScale(1, RoundingMode.HALF_UP);
        String result = "";

        if(so2.compareTo(BigDecimal.valueOf(41)) == LESS){
            result = "Good";
        } else if (so2.compareTo(BigDecimal.valueOf(81)) == LESS) {
            result = "Satisfactory";
        } else if (so2.compareTo(BigDecimal.valueOf(381)) == LESS) {
            result = "Moderate";
        } else if (so2.compareTo(BigDecimal.valueOf(801)) == LESS) {
            result = "Poor";
        } else if (so2.compareTo(BigDecimal.valueOf(1600)) == EQUAL || so2.compareTo(BigDecimal.valueOf(1600)) == LESS) {
            result = "Severe";
        } else if (so2.compareTo(BigDecimal.valueOf(1600)) == MORE) {
            result = "Hazardous";
        }

        return result;
    }

    private String evaluateO3Levels(BigDecimal o3){
        o3 = o3.setScale(1, RoundingMode.HALF_UP);
        String result = "";

        if(o3.compareTo(BigDecimal.valueOf(51)) == LESS){
            result = "Good";
        } else if (o3.compareTo(BigDecimal.valueOf(101)) == LESS) {
            result = "Satisfactory";
        } else if (o3.compareTo(BigDecimal.valueOf(169)) == LESS) {
            result = "Moderate";
        } else if (o3.compareTo(BigDecimal.valueOf(209)) == LESS) {
            result = "Poor";
        } else if (o3.compareTo(BigDecimal.valueOf(748)) == EQUAL || o3.compareTo(BigDecimal.valueOf(748)) == LESS) {
            result = "Severe";
        } else if (o3.compareTo(BigDecimal.valueOf(748)) == MORE) {
            result = "Hazardous";
        }

        return result;
    }



}
