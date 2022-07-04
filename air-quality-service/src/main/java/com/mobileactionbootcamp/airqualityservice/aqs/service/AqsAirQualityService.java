package com.mobileactionbootcamp.airqualityservice.aqs.service;

import com.mobileactionbootcamp.airqualityservice.aqs.dao.AqsAirQualityDocumentDao;
import com.mobileactionbootcamp.airqualityservice.aqs.document.AqsAirQualityDocument;
import com.mobileactionbootcamp.airqualityservice.aqs.document.AqsResults;
import com.mobileactionbootcamp.airqualityservice.cls.model.ClsCategories;
import com.mobileactionbootcamp.airqualityservice.cls.model.ClsCategoriesWrapper;
import com.mobileactionbootcamp.airqualityservice.cls.service.ClsClassificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

        AqsAirQualityDocument aqsAirQualityDocument = getAirQualityDocumentResponse(city, start, end);

        /**
         * todo: convert aqsairqualitydocument to dto and return dto
         * */

        return aqsAirQualityDocument;
    }

    private AqsAirQualityDocument getAirQualityDocumentResponse(String city, LocalDate start, LocalDate end){

        boolean isConsequentDates = false;
        boolean isDocumentInDbChanged = false;
        List<LocalDate> consequentDatesList = new ArrayList<>();

        AqsAirQualityDocument responseDocument = createEmptyDocumentWithCity(city);

        AqsAirQualityDocument documentInDb = getExistingDocumentByCity(city);
        if(documentInDb == null){
            documentInDb = createEmptyDocumentWithCity(city);
        }

        LocalDate incrementedStart = start;
        while(incrementedStart.isBefore(end.plusDays(1))){

            AqsResults existingResultsOfCityByDate = getExistingResultsOfCityByDate(incrementedStart, documentInDb);
            if(existingResultsOfCityByDate != null){
                //databasede var
                responseDocument.addResults(existingResultsOfCityByDate);

                if(consequentDatesList.size() > 0){
                    isConsequentDates = true;
                }
            } else {
                //api dan al!
                consequentDatesList.add(incrementedStart);
            }

            if(incrementedStart.isEqual(end)){
                isConsequentDates = true;
            }

            if(isConsequentDates && consequentDatesList.size() > 0){

                List<AqsResults> resultsFromApi = getAirQualityResultsFromApi(city, consequentDatesList.get(0), consequentDatesList.get(consequentDatesList.size()-1).plusDays(1));
                responseDocument.addMultipleResults(resultsFromApi);
                documentInDb.addMultipleResults(resultsFromApi);
                isDocumentInDbChanged = true;

                isConsequentDates = false;
                consequentDatesList = new ArrayList<>();
            }

            incrementedStart = incrementedStart.plusDays(1);
        }


        if(isDocumentInDbChanged){
            aqsAirQualityDocumentDao.save(documentInDb);
        }

        return responseDocument;
    }

    private AqsAirQualityDocument createEmptyDocumentWithCity(String city){
        AqsAirQualityDocument aqsAirQualityDocument = new AqsAirQualityDocument();
        aqsAirQualityDocument.setCity(city);

        return aqsAirQualityDocument;
    }

    private List<AqsResults> getAirQualityResultsFromApi(String city, LocalDate start, LocalDate end){

        ClsCategoriesWrapper clsCategoriesWrapper = clsClassificationService.getAqiClassification(city, parseDateToString(start), parseDateToString(end));
        List<AqsResults> resultsFromApi = createListOfDailyResultsFromApi(clsCategoriesWrapper);

        return resultsFromApi;
    }

    private List<AqsResults> createListOfDailyResultsFromApi(ClsCategoriesWrapper clsCategoriesWrapper){
        List<AqsResults> resultsFromApi = new ArrayList<>();
        AqsResults dailyResultsFromApi;

        for (ClsCategories categories : clsCategoriesWrapper.getClsCategoriesList()) {
            dailyResultsFromApi = new AqsResults();
            dailyResultsFromApi.setDate(categories.getDate());
            dailyResultsFromApi.addCategory(categories.getCo());
            dailyResultsFromApi.addCategory(categories.getSo2());
            dailyResultsFromApi.addCategory(categories.getO3());

            resultsFromApi.add(dailyResultsFromApi);
        }

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
