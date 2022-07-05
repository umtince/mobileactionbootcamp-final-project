package com.mobileactionbootcamp.airqualityservice.aqs.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileactionbootcamp.airqualityservice.cls.model.ClsBaseCategories;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AqsResults implements Comparable<AqsResults>{

    private String date;
    private List<ClsBaseCategories> categoriesList;

    public AqsResults(){
        this.date = null;
        this.categoriesList = new ArrayList<>();
    }

    public void addCategory(ClsBaseCategories clsBaseCategories){
        this.categoriesList.add(clsBaseCategories);
    }

    @Override
    public int compareTo(AqsResults o) {
        return parseToLocalDate(this.date).compareTo(parseToLocalDate(o.date));
    }

    private LocalDate parseToLocalDate(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(dateString, formatter);
    }
}
