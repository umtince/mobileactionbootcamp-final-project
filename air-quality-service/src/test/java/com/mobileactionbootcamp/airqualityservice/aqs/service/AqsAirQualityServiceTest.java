package com.mobileactionbootcamp.airqualityservice.aqs.service;

import com.mobileactionbootcamp.airqualityservice.aqs.dao.AqsAirQualityDocumentDao;
import com.mobileactionbootcamp.airqualityservice.aqs.document.AqsAirQualityDocument;
import com.mobileactionbootcamp.airqualityservice.aqs.document.AqsResults;
import com.mobileactionbootcamp.airqualityservice.aqs.dto.AqsAirQualityDocumentResponseDto;
import com.mobileactionbootcamp.airqualityservice.cls.model.ClsBaseCategories;
import com.mobileactionbootcamp.airqualityservice.cls.model.ClsCo;
import com.mobileactionbootcamp.airqualityservice.cls.model.ClsO3;
import com.mobileactionbootcamp.airqualityservice.cls.model.ClsSo2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AqsAirQualityServiceTest {

    @InjectMocks
    private AqsAirQualityService aqsAirQualityService;

    @Mock
    private AqsAirQualityDocumentDao aqsAirQualityDocumentDao;

    @Test
    void handleAirQualityDeleteRequest() {

        AqsAirQualityDocument document = Mockito.mock(AqsAirQualityDocument.class);
        document.setCity("Ankara");


        ClsBaseCategories co = new ClsCo();
        co.setChemicalValue("Good");
        ClsBaseCategories so2 = new ClsSo2();
        so2.setChemicalValue("Good");
        ClsBaseCategories o3 = new ClsO3();
        o3.setChemicalValue("Good");

        AqsResults results = new AqsResults();
        results.addCategory(co);
        results.addCategory(so2);
        results.addCategory(o3);
        results.setDate("01-01-2022");

        document.addResults(results);
        String s;

        try {
            s= aqsAirQualityService.handleAirQualityDeleteRequest("Ankara", LocalDate.of(2022,01,01), LocalDate.of(2022,01,01));
        } catch (Exception e){
            s = e.getMessage();
        }

       Assertions.assertEquals("No results found for city" ,s );

    }

}