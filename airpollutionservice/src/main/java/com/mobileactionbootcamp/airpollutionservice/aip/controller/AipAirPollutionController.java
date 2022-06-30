package com.mobileactionbootcamp.airpollutionservice.aip.controller;

import com.mobileactionbootcamp.airpollutionservice.aip.model.AipAirPollution;
import com.mobileactionbootcamp.airpollutionservice.aip.model.Components;
import com.mobileactionbootcamp.airpollutionservice.aip.service.AipAirPollutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/air-pollution-api/v1")
@RequiredArgsConstructor
public class AipAirPollutionController {

    private final AipAirPollutionService aipAirPollutionService;

    @GetMapping("/historical/{location}/{start}/{end}")
    public ResponseEntity getHistoricalAirPollutionData(@PathVariable String location,
                                                        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date start,
                                                        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date end){

        List<Components> components = aipAirPollutionService.getHistoricalAirPollutionData(location, start, end);
        return ResponseEntity.ok(components);
    }
}
