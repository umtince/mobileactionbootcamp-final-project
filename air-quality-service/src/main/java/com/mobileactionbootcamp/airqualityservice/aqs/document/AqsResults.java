package com.mobileactionbootcamp.airqualityservice.aqs.document;

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

    private String date;
    private List<ClsCategories> categoriesList;

    public AqsResults(){
        this.date = null;
        this.categoriesList = new ArrayList<>();
    }

    public void addCategory(ClsCategories clsCategories){
        this.categoriesList.add(clsCategories);
    }
}
