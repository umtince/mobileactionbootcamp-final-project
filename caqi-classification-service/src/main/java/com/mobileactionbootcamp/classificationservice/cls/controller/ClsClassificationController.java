package com.mobileactionbootcamp.classificationservice.cls.controller;

import com.mobileactionbootcamp.classificationservice.cls.model.ClsCategories;
import com.mobileactionbootcamp.classificationservice.cls.service.ClsClassificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/classification/api/v1")
@RequiredArgsConstructor
public class ClsClassificationController {

    private final ClsClassificationService clsClassificationService;

    @GetMapping("/aqi/{location}/{start}/{end}")
    public ResponseEntity getAqiResults(@PathVariable String location,
                                        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate start,
                                        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate end){

        ClsCategories clsCategories = clsClassificationService.getAqiResults(location, start, end);

        return ResponseEntity.ok(clsCategories);
    }
}
