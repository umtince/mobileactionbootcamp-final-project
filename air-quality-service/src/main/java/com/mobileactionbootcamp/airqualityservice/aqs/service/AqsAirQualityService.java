package com.mobileactionbootcamp.airqualityservice.aqs.service;

import com.mobileactionbootcamp.airqualityservice.aqs.converter.IAqsAirQualityDocumentMapper;
import com.mobileactionbootcamp.airqualityservice.aqs.dao.AqsAirQualityDocumentDao;
import com.mobileactionbootcamp.airqualityservice.aqs.document.AqsAirQualityDocument;
import com.mobileactionbootcamp.airqualityservice.aqs.document.AqsResults;
import com.mobileactionbootcamp.airqualityservice.aqs.dto.AqsAirQualityDocumentResponseDto;
import com.mobileactionbootcamp.airqualityservice.cls.model.*;
import com.mobileactionbootcamp.airqualityservice.cls.service.ClsClassificationService;
import com.mobileactionbootcamp.airqualityservice.log.LogMessage;
import com.mobileactionbootcamp.airqualityservice.log.MQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AqsAirQualityService {

    private final AqsAirQualityDocumentDao aqsAirQualityDocumentDao;
    private final ClsClassificationService clsClassificationService;
    private final AmqpTemplate template;
    private final LocalDate CONTROL_DATE = LocalDate.of(2020,11,27);

    @Value("${rabbit.queue.name}")
    public String QUEUE;

    @Value("${rabbit.exchange.name}")
    public String EXCHANGE;

    @Value("${rabbit.routingkey.name}")
    public String ROUTING_KEY;

    public String handleAirQualityDeleteRequest(String city, LocalDate start, LocalDate end) throws Exception{
        checkDates(start, end);

        AqsAirQualityDocument documentInDbToBeAltered = getExistingDocumentByCity(city);

        if(documentInDbToBeAltered == null){
            throw new Exception("No results found for city");
        }

        LocalDate incrementedStart = start;
        while(incrementedStart.isBefore(end.plusDays(1))){
            removeElementFromDocumentByDate(documentInDbToBeAltered, incrementedStart);
            incrementedStart = incrementedStart.plusDays(1);
        }

        aqsAirQualityDocumentDao.save(documentInDbToBeAltered);

        return "Entries successfully deleted.";
    }

    private void removeElementFromDocumentByDate(AqsAirQualityDocument document, LocalDate date){
        document.getResults().removeIf(e -> e.getDate().equals(parseDateToString(date)));
    }


    public AqsAirQualityDocumentResponseDto handleAirQualityRequest(String city, LocalDate start, LocalDate end) throws Exception{

        checkDates(start, end);

        AqsAirQualityDocument aqsAirQualityDocument = getAirQualityDocumentResponse(city, start, end);

        return IAqsAirQualityDocumentMapper.INSTANCE.convertToAqsAirQualityDocumentResponseDto(aqsAirQualityDocument);
    }

    private void checkDates(LocalDate start, LocalDate end) throws Exception{
        if(start.isBefore(CONTROL_DATE) || end.isBefore(CONTROL_DATE)){
            throw new Exception("Dates can not be before than November 27th 2020");
        } else if(end.isBefore(start)){
            throw new Exception("End date can not be before start date!");
        }
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
                sendLogMessage(parseDateToString(incrementedStart),city,"Returned from Database");

                if(consequentDatesList.size() > 0){
                    isConsequentDates = true;
                }
            } else {
                //api dan alınacaklar listesine ekle!
                consequentDatesList.add(incrementedStart);
                sendLogMessage(parseDateToString(incrementedStart),city,"Returned from API");
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
            Collections.sort(documentInDb.getResults());
            aqsAirQualityDocumentDao.save(documentInDb);
        }

        Collections.sort(responseDocument.getResults());
        return responseDocument;
    }

    private void sendLogMessage(String date, String city, String returnedFrom){
        LogMessage logMessage = new LogMessage();
        logMessage.setLogId(UUID.randomUUID().toString());
        logMessage.setCity(city);
        logMessage.setDate(date);
        logMessage.setReturnedFrom(returnedFrom);

        template.convertAndSend(EXCHANGE, ROUTING_KEY, logMessage);
    }

    private AqsAirQualityDocument createEmptyDocumentWithCity(String city){
        AqsAirQualityDocument aqsAirQualityDocument = new AqsAirQualityDocument();
        aqsAirQualityDocument.setCity(city);

        return aqsAirQualityDocument;
    }

    private List<AqsResults> getAirQualityResultsFromApi(String city, LocalDate start, LocalDate end){

        ClsCategoriesWrapper clsCategoriesWrapper = clsClassificationService.getAqiClassification(city, parseDateToString(start), parseDateToString(end));
        return createListOfDailyResultsFromApi(clsCategoriesWrapper);
    }

    private List<AqsResults> createListOfDailyResultsFromApi(ClsCategoriesWrapper clsCategoriesWrapper){
        List<AqsResults> resultsFromApi = new ArrayList<>();
        AqsResults dailyResultsFromApi;

        for (ClsCategories categories : clsCategoriesWrapper.getClsCategoriesList()) {
            dailyResultsFromApi = new AqsResults();
            dailyResultsFromApi.setDate(categories.getDate());

            setDailyResults(new ClsCo(), dailyResultsFromApi, categories.getCo());
            setDailyResults(new ClsSo2(), dailyResultsFromApi, categories.getSo2());
            setDailyResults(new ClsO3(), dailyResultsFromApi, categories.getO3());

            resultsFromApi.add(dailyResultsFromApi);
        }

        return resultsFromApi;
    }

    private void setDailyResults(ClsBaseCategories clsBaseCategories, AqsResults dailyResults, String chemicalValue){
        clsBaseCategories.setChemicalValue(chemicalValue);
        dailyResults.addCategory(clsBaseCategories);
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
