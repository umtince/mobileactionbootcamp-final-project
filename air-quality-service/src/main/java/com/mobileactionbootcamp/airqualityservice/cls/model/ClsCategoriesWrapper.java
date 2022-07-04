package com.mobileactionbootcamp.airqualityservice.cls.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClsCategoriesWrapper {
    private List<ClsCategories> clsCategoriesList;

    public ClsCategoriesWrapper(){
        this.clsCategoriesList = new ArrayList<>();
    }
}