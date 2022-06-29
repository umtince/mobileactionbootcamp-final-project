package com.mobileactionbootcamp.geocodingservice.geo.controller;

import com.mobileactionbootcamp.geocodingservice.geo.model.GeoGeocoding;
import com.mobileactionbootcamp.geocodingservice.geo.service.GeoGeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geocoding-api/v1")
@RequiredArgsConstructor
public class GeoGeocodingController {

    private final GeoGeocodingService geoGeocodingService;

    @GetMapping("/location/{location}")
    public ResponseEntity getCoordinatesByLocationName(@RequestParam String location){

        return ResponseEntity.ok(geoGeocodingService.getCoordinatesByLocationName(location));
    }
}
