package com.mobileactionbootcamp.airqualityservice.aqs.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "AirQuality")
public class AqsAirQualityDocument {

    @Id
    private String id;
    private String city;
    private List<AqsResults> results;

    public AqsAirQualityDocument(){
       this.results = new ArrayList<>();
    }

    public void addResuls(AqsResults aqsResults){
        this.results.add(aqsResults);
    }
}
