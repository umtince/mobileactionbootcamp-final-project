package com.mobileactionbootcamp.geocodingservice.geo.controller;

import com.mobileactionbootcamp.geocodingservice.geo.service.GeoGeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/geocoding-api/v1")
@RequiredArgsConstructor
public class GeoGeocodingController {

    private final GeoGeocodingService geoGeocodingService;

    @GetMapping("/location/{location}")
    public ResponseEntity getCoordinatesByLocationName(@PathVariable String location){

        return ResponseEntity.ok(geoGeocodingService.getCoordinatesByLocationName(location));
    }
}
