package com.mobileactionbootcamp.airqualityservice.aqs.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document(collection = "AirQuality")
public class AqsAirQualityDocument {

    @Id
    private String id;
    private String city;
    /**
     *
     * NEEDS TO BE UPDATED
     * */
}
