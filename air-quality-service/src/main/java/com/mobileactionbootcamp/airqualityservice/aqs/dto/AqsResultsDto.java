package com.mobileactionbootcamp.airqualityservice.aqs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.mobileactionbootcamp.airqualityservice.cls.model.ClsBaseCategories;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonRootName("Results")
public class AqsResultsDto {
    @JsonProperty("Date")
    private String date;
    @JsonProperty("Categories")
    private List<ClsBaseCategories> categoriesList;

    public AqsResultsDto(){
        this.date = null;
        this.categoriesList = new ArrayList<>();
    }
}
