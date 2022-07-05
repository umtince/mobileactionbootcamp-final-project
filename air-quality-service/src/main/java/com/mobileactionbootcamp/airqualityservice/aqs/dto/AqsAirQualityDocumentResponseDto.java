package com.mobileactionbootcamp.airqualityservice.aqs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileactionbootcamp.airqualityservice.aqs.document.AqsResults;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AqsAirQualityDocumentResponseDto {

    @JsonProperty("City")
    private String city;
    @JsonProperty("Results")
    private List<AqsResultsDto> results;

    public String getCity() {
        return city;
    }

    public List<AqsResultsDto> getResults() {
        return results;
    }
}
