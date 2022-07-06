package com.mobileactionbootcamp.logservice.dao;

import com.mobileactionbootcamp.logservice.document.LogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogDocumentDao extends MongoRepository<LogDocument, String>{
}

