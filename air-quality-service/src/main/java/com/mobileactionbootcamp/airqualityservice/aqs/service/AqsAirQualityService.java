package com.mobileactionbootcamp.airqualityservice.aqs.service;

import com.mobileactionbootcamp.airqualityservice.aqs.dao.AqsAirQualityDocumentDao;
import com.mobileactionbootcamp.airqualityservice.aqs.document.AqsAirQualityDocument;
import com.mobileactionbootcamp.airqualityservice.aqs.document.AqsResults;
import com.mobileactionbootcamp.airqualityservice.cls.model.ClsBaseComponents;
import com.mobileactionbootcamp.airqualityservice.cls.model.ClsCategories;
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

        /*AqsAirQualityDocument aqsAirQualityDocument = new AqsAirQualityDocument();
        aqsAirQualityDocument.setCity(city);

        AqsResults aqsResults = new AqsResults();
        aqsResults.setDate(parseDateToString(start));

        ClsCategories tempCategories = new ClsCategories();
        tempCategories.setCo(new ClsBaseComponents("Good"));
        tempCategories.setO3(new ClsBaseComponents("Good"));
        tempCategories.setSo2(new ClsBaseComponents("Good"));

        aqsResults.addCategory(tempCategories);

        //aqsAirQualityDocument.addResuls(aqsResults);

        List<AqsAirQualityDocument> aqsAirQualityDocumentList = aqsAirQualityDocumentDao.findAll();

        for(AqsAirQualityDocument document : aqsAirQualityDocumentList){
            if(document.getCity().equals(aqsAirQualityDocument.getCity())){

                document.getResults().add(aqsResults);
                document = aqsAirQualityDocumentDao.save(document);
                return document;
            }
        }
        aqsAirQualityDocument.addResuls(aqsResults);
        aqsAirQualityDocumentDao.save(aqsAirQualityDocument);*/

        AqsAirQualityDocument aqsAirQualityDocument = getAirQualityDocumentWithResults(city, start, end);
        //aqsAirQualityDocumentDao.save(aqsAirQualityDocument);

        return aqsAirQualityDocument;
    }

    private AqsAirQualityDocument getAirQualityDocumentWithResults(String city, LocalDate start, LocalDate end){

        //DB request is made at this point to prevent repeating DB access (performance optimization).
        List<AqsAirQualityDocument> aqsAirQualityDocumentList = aqsAirQualityDocumentDao.findAll();
        LocalDate incremenentedStart = start;
        AqsResults existingResultByDate;
        AqsAirQualityDocument responseDocument = createEmptyDocument();
        AqsAirQualityDocument documentInDb = getExistingDocumentByCity(city, aqsAirQualityDocumentList);

        if(documentInDb == null){
            documentInDb = createEmptyDocument();
        }

        responseDocument.setCity(city);
        //responseDocument.setId(getExistingDocumentIdByCity(city, aqsAirQualityDocumentList));

        while (incremenentedStart.isBefore(end)){

            existingResultByDate = getExistingAirQualityResultsByDate(city, incremenentedStart, aqsAirQualityDocumentList);

            if (existingResultByDate != null) {
                //returned from database
                responseDocument.addResuls(existingResultByDate);

                /**
                 * TODO: log returned from db
                 * */
            }else {
                // returned from api
                /*AqsResults resultsFromApi = new AqsResults();
                resultsFromApi.setDate(parseDateToString(incremenentedStart));
                resultsFromApi.addCategory(clsClassificationService.getAqiClassification(city, parseDateToString(incremenentedStart), parseDateToString(incremenentedStart.plusDays(1))));*/

                AqsResults aqsResults;
                aqsResults = getResultsFromApi(city, incremenentedStart, incremenentedStart.plusDays(1));
                responseDocument.addResuls(aqsResults);
                documentInDb.addResuls(aqsResults);
                documentInDb = aqsAirQualityDocumentDao.save(documentInDb);
                /**
                 * TODO: log returned from api
                 * */
            }

            incremenentedStart = incremenentedStart.plusDays(1);
        }

        return responseDocument;
    }

    private AqsAirQualityDocument getExistingDocumentByCity(String city, List<AqsAirQualityDocument> aqsAirQualityDocumentList){
        for(AqsAirQualityDocument document : aqsAirQualityDocumentList){
            if(document.getCity().equals(city)){
                return document;
            }
        }
        return null;
    }

    private String getExistingDocumentIdByCity(String city, List<AqsAirQualityDocument> aqsAirQualityDocumentList){
        for(AqsAirQualityDocument document : aqsAirQualityDocumentList){
            if(document.getCity().equals(city)){
                return document.getId();
            }
        }
        return null;
    }

    private AqsResults getResultsFromApi(String city, LocalDate start, LocalDate end){
        AqsResults resultsFromApi = new AqsResults();
        resultsFromApi.setDate(parseDateToString(start));
        resultsFromApi.addCategory(clsClassificationService.getAqiClassification(city, parseDateToString(start), parseDateToString(end)));

        return resultsFromApi;
    }

    private AqsAirQualityDocument createEmptyDocument(){

       /* ClsCategories clsCategories = new ClsCategories();

        AqsResults aqsResults = new AqsResults();
        aqsResults.addCategory(clsCategories);*/

        AqsAirQualityDocument aqsAirQualityDocument = new AqsAirQualityDocument();
        //aqsAirQualityDocument.addResuls(aqsResults);

        return aqsAirQualityDocument;
    }

    private AqsResults getExistingAirQualityResultsByDate(String city, LocalDate date, List<AqsAirQualityDocument> aqsAirQualityDocumentList){

        for(AqsAirQualityDocument document : aqsAirQualityDocumentList){
            if(document.getCity().equals(city)){
                for(AqsResults aqsResults : document.getResults()){
                    if(aqsResults.getDate().equals(date)){
                        return aqsResults;
                    }
                }
            }
        }

        return null;
    }

    private String parseDateToString(LocalDate date){
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(DATE_FORMATTER);
    }
}
