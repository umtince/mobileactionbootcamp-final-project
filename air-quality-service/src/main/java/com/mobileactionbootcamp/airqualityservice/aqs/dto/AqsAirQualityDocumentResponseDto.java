package com.mobileactionbootcamp.airqualityservice.aqs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileactionbootcamp.airqualityservice.aqs.document.AqsResults;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AqsAirQualityDocumentResponseDto {

    private String city;
    private List<AqsResults> results;

    public String getCity() {
        return city;
    }

    @JsonProperty("")
    public List<AqsResults> getResults() {
        return results;
    }
}
