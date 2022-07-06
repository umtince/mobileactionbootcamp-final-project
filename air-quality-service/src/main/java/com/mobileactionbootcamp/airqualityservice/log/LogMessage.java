package com.mobileactionbootcamp.airqualityservice.log;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogMessage implements Serializable {

    private String logId;
    private String date;
    private String city;
    private String returnedFrom;
}
