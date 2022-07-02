package com.mobileactionbootcamp.airqualityservice.aqs.service;

import com.mobileactionbootcamp.airqualityservice.aqs.dao.AqsAirQualityDocumentDao;
import com.mobileactionbootcamp.airqualityservice.aqs.document.AqsAirQualityDocument;
import com.mobileactionbootcamp.airqualityservice.aqs.document.AqsResults;
import com.mobileactionbootcamp.airqualityservice.cls.service.ClsClassificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AqsAirQualityService {

    private final AqsAirQualityDocumentDao aqsAirQualityDocumentDao;
    private final ClsClassificationService clsClassificationService;
    private  LocalDate CONTROL_DATE = LocalDate.of(2020,11,27);

    public AqsAirQualityDocument handleAirQualityRequest(String city, LocalDate start, LocalDate end) throws Exception{

        if(start.isBefore(CONTROL_DATE)){
            throw new Exception("Start date can not be before than November 27th 2020");
        }
        else if(end.isBefore(start)){
            throw new Exception("End date can not be before start date!");
        }

        AqsAirQualityDocument aqsAirQualityDocument = getAirQualityDocumentWithResults(city, start, end);

        return aqsAirQualityDocument;
    }

    private AqsAirQualityDocument getAirQualityDocumentWithResults(String city, LocalDate start, LocalDate end){

        AqsAirQualityDocument responseDocument = new AqsAirQualityDocument();
        responseDocument.setCity(city);

        AqsAirQualityDocument documentInDb = getExistingDocumentByCity(city);
        if(documentInDb == null){
            documentInDb = new AqsAirQualityDocument();
            documentInDb.setCity(city);
        }

        LocalDate incrementedStart = start;
        while(incrementedStart.isBefore(end.plusDays(1))){

            AqsResults existingResultsOfCity = getExistingResultsOfCityByDate(incrementedStart, documentInDb);
            if(existingResultsOfCity != null){
                //databasede var
                responseDocument.addResuls(existingResultsOfCity);
            } else {
                //api dan al!
                AqsResults resultsFromApi = getAirQualityResultsFromApi(city, incrementedStart, incrementedStart.plusDays(1));
                responseDocument.addResuls(resultsFromApi);
                documentInDb.addResuls(resultsFromApi);
                documentInDb = aqsAirQualityDocumentDao.save(documentInDb);
            }

            incrementedStart = incrementedStart.plusDays(1);
        }

        return responseDocument;
    }

    private AqsResults getAirQualityResultsFromApi(String city, LocalDate start, LocalDate end){
        AqsResults resultsFromApi = new AqsResults();
        resultsFromApi.setDate(parseDateToString(start));
        resultsFromApi.addCategory(clsClassificationService.getAqiClassification(city, parseDateToString(start), parseDateToString(end)));

        return resultsFromApi;
    }

    private AqsResults getExistingResultsOfCityByDate(LocalDate date, AqsAirQualityDocument documentInDb){
        String s_date = parseDateToString(date);

        for(AqsResults results : documentInDb.getResults()){
            if(results.getDate().equals(s_date)){
                return results;
            }
        }

        return null;
    }

    private AqsAirQualityDocument getExistingDocumentByCity(String city){
        List<AqsAirQualityDocument> aqsAirQualityDocumentList = aqsAirQualityDocumentDao.findAll();

        for(AqsAirQualityDocument document : aqsAirQualityDocumentList){
            if(document.getCity().equals(city)){
                return document;
            }
        }

        return null;
    }

    private String parseDateToString(LocalDate date){
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(DATE_FORMATTER);
    }
}
