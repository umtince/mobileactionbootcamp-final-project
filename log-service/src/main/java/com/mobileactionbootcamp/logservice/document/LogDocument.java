package com.mobileactionbootcamp.logservice.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("log")
public class LogDocument {
    @Id
    private String id;
    private String city;
    private String date;
    private String returnedFrom;
}
