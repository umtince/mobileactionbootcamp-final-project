package com.mobileactionbootcamp.airqualityservice.aqs.controller;

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

    @GetMapping
    public ResponseEntity getAirQuality(@PathVariable String city,
                                        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate start,
                                        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate end){

        try {
            aqsAirQualityService.handleAirQualityRequest(city,start,end);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("OK");
    }
}
