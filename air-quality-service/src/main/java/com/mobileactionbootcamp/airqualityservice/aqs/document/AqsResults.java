package com.mobileactionbootcamp.airqualityservice.aqs.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileactionbootcamp.airqualityservice.cls.model.ClsCategories;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AqsResults {

    @JsonProperty("Date")
    private String date;
    @JsonProperty("Categories")
    private List<ClsCategories> categoriesList;

    public AqsResults(){
        this.date = null;
        this.categoriesList = new ArrayList<>();
    }

    public void addCategory(ClsCategories clsCategories){
        this.categoriesList.add(clsCategories);
    }
}
