package com.mobileactionbootcamp.airqualityservice.aqs.dao;

import com.mobileactionbootcamp.airqualityservice.aqs.document.AqsAirQualityDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AqsAirQualityDocumentDao extends MongoRepository<AqsAirQualityDocument, String> {
}
