package com.mobileactionbootcamp.airpollutionservice.aip.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AipDailyComponents {
    private String date;
    private Components components;
}
