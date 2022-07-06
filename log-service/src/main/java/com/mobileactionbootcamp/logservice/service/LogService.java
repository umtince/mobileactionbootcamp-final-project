package com.mobileactionbootcamp.logservice.service;

import com.mobileactionbootcamp.log.LogMessage;
import com.mobileactionbootcamp.logservice.dao.LogDocumentDao;
import com.mobileactionbootcamp.logservice.document.LogDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {

    private final LogDocumentDao logDocumentDao;


    @RabbitListener(queues = "log_queue3")
    public void listener(LogMessage logMessage){
        logDb(logMessage);
        log.info(logMessage.getCity() + "\n" + logMessage.getDate() + "\n" + logMessage.getReturnedFrom() + "\n\n");
    }

    public void logDb(LogMessage logMessage){
        LogDocument logDocument = new LogDocument();

        logDocument.setCity(logMessage.getCity());
        logDocument.setDate(logMessage.getDate());
        logDocument.setReturnedFrom(logMessage.getReturnedFrom());

        logDocumentDao.save(logDocument);
    }
}
