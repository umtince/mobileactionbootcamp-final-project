package com.mobileactionbootcamp.airpollutionservice.aip.controller;

import com.mobileactionbootcamp.airpollutionservice.aip.model.AipDailyComponents;
import com.mobileactionbootcamp.airpollutionservice.aip.model.AipDailyComponentsWrapper;
import com.mobileactionbootcamp.airpollutionservice.aip.model.Components;
import com.mobileactionbootcamp.airpollutionservice.aip.service.AipAirPollutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/air-pollution-api/v1")
@RequiredArgsConstructor
public class AipAirPollutionController {

    private final AipAirPollutionService aipAirPollutionService;

    @GetMapping("/historical/{location}/{start}/{end}")
    public ResponseEntity getHistoricalAirPollutionData(@PathVariable String location,
                                                        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate start,
                                                        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate end){

        AipDailyComponentsWrapper aipDailyComponentsWrapper = aipAirPollutionService.getHistoricalAirPollutionData(location, start, end);
        return ResponseEntity.ok(aipDailyComponentsWrapper);
    }
}
