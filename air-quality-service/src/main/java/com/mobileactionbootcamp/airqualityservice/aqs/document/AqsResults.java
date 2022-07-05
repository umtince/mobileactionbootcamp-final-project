package com.mobileactionbootcamp.airqualityservice.aqs.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileactionbootcamp.airqualityservice.cls.model.ClsBaseCategories;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AqsResults {

    private String date;
    private List<ClsBaseCategories> categoriesList;

    public AqsResults(){
        this.date = null;
        this.categoriesList = new ArrayList<>();
    }

    public void addCategory(ClsBaseCategories clsBaseCategories){
        this.categoriesList.add(clsBaseCategories);
    }
}
