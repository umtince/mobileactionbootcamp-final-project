package com.mobileactionbootcamp.airqualityservice.aqs.controller;

import com.mobileactionbootcamp.airqualityservice.aqs.dto.AqsAirQualityDocumentResponseDto;
import com.mobileactionbootcamp.airqualityservice.aqs.service.AqsAirQualityService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/air-quality-api/v1")
@RequiredArgsConstructor
public class AqsAirQualityController {

    private final AqsAirQualityService aqsAirQualityService;

    @GetMapping("/air-quality/{city}/{start}/{end}")
    public ResponseEntity<AqsAirQualityDocumentResponseDto> getAirQuality(@PathVariable String city,
                                        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate start,
                                        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate end){

        AqsAirQualityDocumentResponseDto aqsAirQualityDocumentResponseDto;

        try {
            aqsAirQualityDocumentResponseDto = aqsAirQualityService.handleAirQualityRequest(city,start,end);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(aqsAirQualityDocumentResponseDto, HttpStatus.OK);
    }

    @GetMapping("/air-quality/{city}")
    public ResponseEntity<AqsAirQualityDocumentResponseDto> getAirQualityWithoutDates(@PathVariable String city){

        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(6);

        AqsAirQualityDocumentResponseDto aqsAirQualityDocumentResponseDto;

        try {
            aqsAirQualityDocumentResponseDto = aqsAirQualityService.handleAirQualityRequest(city,start,end);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(aqsAirQualityDocumentResponseDto, HttpStatus.OK);
    }
}
