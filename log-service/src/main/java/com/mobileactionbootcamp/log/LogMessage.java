package com.mobileactionbootcamp.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
