package com.mobileactionbootcamp.airqualityservice.aqs.converter;

import com.mobileactionbootcamp.airqualityservice.aqs.document.AqsAirQualityDocument;
import com.mobileactionbootcamp.airqualityservice.aqs.dto.AqsAirQualityDocumentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AqsAirQualityDocumentMapper {
    AqsAirQualityDocumentMapper INSTANCE = Mappers.getMapper(AqsAirQualityDocumentMapper.class);

    AqsAirQualityDocumentResponseDto convertToAqsAirQualityDocumentResponseDto(AqsAirQualityDocument aqsAirQualityDocument);
}
