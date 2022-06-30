package com.mobileactionbootcamp.classificationservice.cls.controller;

import com.mobileactionbootcamp.classificationservice.cls.model.ClsCategories;
import com.mobileactionbootcamp.classificationservice.cls.service.ClsClassificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/classification/api/v1")
@RequiredArgsConstructor
public class ClsClassificationController {

    private final ClsClassificationService clsClassificationService;

    @GetMapping("/aqi/{location}/{start}/{end}")
    public ResponseEntity getAqiResults(@PathVariable String location,
                                        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date start,
                                        @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date end){

        ClsCategories clsCategories = clsClassificationService.getAqiResults(location, start, end);

        return ResponseEntity.ok(null);
    }
}
