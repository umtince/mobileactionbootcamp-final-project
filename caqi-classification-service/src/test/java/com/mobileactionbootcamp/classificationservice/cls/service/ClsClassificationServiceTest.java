package com.mobileactionbootcamp.classificationservice.cls.service;

import com.mobileactionbootcamp.classificationservice.aip.service.AipAirPollutionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;


@ExtendWith(MockitoExtension.class)
class ClsClassificationServiceTest {

    @InjectMocks
    private ClsClassificationService clsClassificationService;

    @Mock
    private AipAirPollutionService aipAirPollutionService;

    @Test
    void parseDateToString() {
        Assertions.assertEquals("01-01-2022", clsClassificationService.parseDateToString(LocalDate.of(2022,01,01)));
    }

    @Test
    void evaluateCoLevels() {
        BigDecimal value = BigDecimal.valueOf(0);
        Assertions.assertEquals("Good", clsClassificationService.evaluateCoLevels(value));
    }

    @Test
    void evaluateCoLevels2() {
        BigDecimal value = BigDecimal.valueOf(2);
        Assertions.assertEquals("Satisfactory", clsClassificationService.evaluateCoLevels(value));
    }

    @Test
    void evaluateCoLevels3() {
        BigDecimal value = BigDecimal.valueOf(5);
        Assertions.assertEquals("Moderate", clsClassificationService.evaluateCoLevels(value));
    }

    @Test
    void evaluateCoLevels4() {
        BigDecimal value = BigDecimal.valueOf(17);
        Assertions.assertEquals("Poor", clsClassificationService.evaluateCoLevels(value));
    }

    @Test
    void evaluateCoLevels5() {
        BigDecimal value = BigDecimal.valueOf(34);
        Assertions.assertEquals("Severe", clsClassificationService.evaluateCoLevels(value));
    }

    @Test
    void evaluateCoLevels6() {
        BigDecimal value = BigDecimal.valueOf(40);
        Assertions.assertEquals("Hazardous", clsClassificationService.evaluateCoLevels(value));
    }


    @Test
    void evaluateSo2Levels() {
        BigDecimal value = BigDecimal.valueOf(40);
        Assertions.assertEquals("Good", clsClassificationService.evaluateSo2Levels(value));
    }

    @Test
    void evaluateSo2Levels2() {
        BigDecimal value = BigDecimal.valueOf(80);
        Assertions.assertEquals("Satisfactory", clsClassificationService.evaluateSo2Levels(value));
    }

    @Test
    void evaluateSo2Levels3() {
        BigDecimal value = BigDecimal.valueOf(380);
        Assertions.assertEquals("Moderate", clsClassificationService.evaluateSo2Levels(value));
    }

    @Test
    void evaluateSo2Levels4() {
        BigDecimal value = BigDecimal.valueOf(555);
        Assertions.assertEquals("Poor", clsClassificationService.evaluateSo2Levels(value));
    }

    @Test
    void evaluateSo2Levels5() {
        BigDecimal value = BigDecimal.valueOf(1600);
        Assertions.assertEquals("Severe", clsClassificationService.evaluateSo2Levels(value));
    }

    @Test
    void evaluateSo2Levels6() {
        BigDecimal value = BigDecimal.valueOf(1601);
        Assertions.assertEquals("Hazardous", clsClassificationService.evaluateSo2Levels(value));
    }

    @Test
    void evaluateO3Levels() {
        BigDecimal value = BigDecimal.valueOf(50);
        Assertions.assertEquals("Good", clsClassificationService.evaluateO3Levels(value));
    }

    @Test
    void evaluateO3Levels2() {
        BigDecimal value = BigDecimal.valueOf(100);
        Assertions.assertEquals("Satisfactory", clsClassificationService.evaluateO3Levels(value));
    }

    @Test
    void evaluateO3Levels3() {
        BigDecimal value = BigDecimal.valueOf(160);
        Assertions.assertEquals("Moderate", clsClassificationService.evaluateO3Levels(value));
    }

    @Test
    void evaluateO3Levels4() {
        BigDecimal value = BigDecimal.valueOf(200);
        Assertions.assertEquals("Poor", clsClassificationService.evaluateO3Levels(value));
    }

    @Test
    void evaluateO3Levels5() {
        BigDecimal value = BigDecimal.valueOf(748);
        Assertions.assertEquals("Severe", clsClassificationService.evaluateO3Levels(value));
    }

    @Test
    void evaluateO3Levels6() {
        BigDecimal value = BigDecimal.valueOf(750);
        Assertions.assertEquals("Hazardous", clsClassificationService.evaluateO3Levels(value));
    }

}